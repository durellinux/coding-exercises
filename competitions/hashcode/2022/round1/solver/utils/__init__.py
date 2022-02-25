from heapq import heappush, heappop


class ProblemData:
    projects = {}
    projects_queue = []
    contributors = []
    contributors_by_name = {}
    scheduled_projects = []

    def heuristic(self, project_data):
        return project_data['deadline']

    def requeue_heuristic(self, project_data):
        if self.has_project():
            next = self.get_next()
            value = next['deadline'] + 1
            heappush(self.projects_queue, (self.heuristic(next), next['name']))
            return value
        return None

    def set_projects(self, projects):
        for p in projects:
            try:
                heuristic_value = self.heuristic(p)
                heappush(self.projects_queue, (heuristic_value, p['name']))
            except Exception as e:
                print(e)
                raise e
            self.projects[p['name']] = p
            p['retries'] = 0

    def has_project(self):
        return len(self.projects_queue) > 0

    def get_next(self):
        project_name = heappop(self.projects_queue)[1]
        return self.projects[project_name]

    def try_requeue(self, project_data):
        if self.should_requeue(project_data):
            project_data['retries'] += 1
            heuristic_value = self.requeue_heuristic(project_data)
            if heuristic_value is not None:
                heappush(self.projects_queue, (heuristic_value, project_data['name']))

    def should_requeue(self, project_data):
        return project_data['retries'] < 5

    def set_contributors(self, contributors):
        self.contributors = contributors
        for c in contributors:
            self.contributors_by_name[c['name']] = c

    def get_possible_contributors(self, project_data):
        contributors_per_role = {}
        role_mentors = {}

        for r in project_data['roles_list']:
            contributors_per_role[r['id']] = []
            role_mentors[r['id']] = []

        for c in self.contributors:
            c['mentor_roles'] = set()
            for r in project_data['roles_list']:
                if r['level'] == 1 or (r['name'] in c['skills_dict'] and c['skills_dict'][r['name']] >= r['level'] - 1):
                    contributors_per_role[r['id']].append(c)
                    if r['name'] in c['skills_dict'] and c['skills_dict'][r['name']] >= r['level']:
                        role_mentors[r['id']].append(c['name'])
                        c['mentor_roles'].add(r['id'])

        return (contributors_per_role, role_mentors)

    def schedule(self, next_project, role_mapping):
        self.scheduled_projects.append((next_project, role_mapping))
        self.learn(next_project, role_mapping)

    def learn(self, next_project, role_mapping):
        for role in next_project['roles_list']:
            try:
                role_id = role['id']
                role_name = role['name']
                required_level = next_project['roles_dict'][role_id]['level']
                contributor = self.contributors_by_name[role_mapping[role_id]]
                contributor_level = contributor['skills_dict'][role_name] if role_name in contributor['skills_dict'] else 0

                if contributor_level <= required_level:
                    contributor['skills_dict'][role_name] = contributor_level + 1
            except Exception as e:
                print(e)
                raise e

    def print_solution(self):
        print(len(self.scheduled_projects))
        for s in self.scheduled_projects:
            project = s[0]
            mapping = s[1]
            print(project['name'])
            assignments = []
            for r in project['roles_list']:
                assignments.append(mapping[r['id']])
            print(" ".join(assignments))
