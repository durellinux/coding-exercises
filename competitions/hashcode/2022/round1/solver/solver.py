import sys
from heapq import heappush, heappop

from utils import ProblemData

def parse(file):
    line = file.readline().strip()
    [contributors, projects] = map(int, line.split())

    contributors_data = []
    for c in range(contributors):
        line = file.readline().strip()

        [name, skills_number_str] = line.split()
        skills_number = int(skills_number_str)

        contributor = {}

        contributor['name'] = name
        contributor['skill_number'] = skills_number
        contributor['skills_dict'] = {}
        contributor['skills_list'] = []

        for s in range(skills_number):
            line = file.readline().strip()

            [skill_name, skill_level_str] = line.split()
            skill_level = int(skill_level_str)

            skill_data = {}
            skill_data['name'] = skill_name
            skill_data['level'] = skill_level
            contributor['skills_dict'][skill_name] = skill_level
            contributor['skills_list'].append(skill_data)

        contributors_data.append(contributor)

    projects_data = []
    for p in range(projects):
        line = file.readline().strip()
        [name, duration_str, score_str, deadline_str, roles_number_str] = line.strip().split()
        duration = int(duration_str)
        score = int(score_str)
        deadline = int(deadline_str)
        roles_number = int(roles_number_str)

        project = {}
        project['name'] = name
        project['duration'] = duration
        project['score'] = score
        project['deadline'] = deadline
        project['roles_number'] = roles_number
        project['roles_list'] = []
        project['roles_dict'] = {}

        for r in range(roles_number):
            line = file.readline().strip()
            [name, level_str] = line.split()
            level = int(level_str)

            role = {}
            role['name'] = name
            role['level'] = level
            role['id'] = r
            project['roles_list'].append(role)
            project['roles_dict'][r] = role

        projects_data.append(project)

    problem_data = ProblemData()
    problem_data.set_projects(projects_data)
    problem_data.set_contributors(contributors_data)

    return problem_data


def solve(file):
    problem_data = parse(file)

    while problem_data.has_project():
        next_project = problem_data.get_next()
        (contributors_per_role, role_mentors) = problem_data.get_possible_contributors(next_project)

        roles = set(r['id'] for r in next_project['roles_list'])
        assigned_contributors=set()

        role_mapping = {}

        roles_priority = []
        for r in contributors_per_role.keys():
            heappush(roles_priority, (len(contributors_per_role[r]), r))

        while len(roles_priority) > 0:
            r = heappop(roles_priority)[1]
            if r not in roles:
                continue

            mentor_searched = False
            for c in contributors_per_role[r]:
                role = next_project['roles_list'][r]
                if c['name'] in assigned_contributors:
                    continue
                try:
                    if role['name'] in c['skills_dict'] and c['skills_dict'][role['name']] >= role['level']:
                        role_mapping[r] = c['name']
                        assigned_contributors.add(c['name'])
                        roles.remove(r)
                        break
                    else:
                        if mentor_searched == True:
                            continue

                        mentor_searched = True
                        mentor_found = False
                        for c2name in role_mentors[r]:
                            c2 = problem_data.contributors_by_name[c2name]
                            if c2['name'] != c['name'] and c2['name'] in role_mentors[r] and c2['name'] not in assigned_contributors:
                                for r2 in c2['mentor_roles']:
                                    if r2 != r and r2 in roles:
                                        role_mapping[r] = c['name']
                                        role_mapping[r2] = c2['name']
                                        assigned_contributors.add(c['name'])
                                        assigned_contributors.add(c2['name'])
                                        roles.remove(r)
                                        roles.remove(r2)
                                        mentor_found = True
                                        break

                                if mentor_found:
                                    break

                        if mentor_found:
                            break
                except Exception as e:
                    print(e)
                    raise e

        if len(roles) == 0:
            problem_data.schedule(next_project, role_mapping)
        else:
            problem_data.try_requeue(next_project)


    problem_data.print_solution()

with open(sys.argv[1]) as file:
    solve(file)
