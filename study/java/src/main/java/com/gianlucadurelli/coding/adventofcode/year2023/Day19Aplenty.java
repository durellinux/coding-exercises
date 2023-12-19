package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class Day19Aplenty {

    record MatchOperation(boolean terminal, boolean accepted, String nextWorkflow) {
        public static MatchOperation of(String s) {
            if (s.equals("A")) {
                return new MatchOperation(true, true, "");
            }

            if (s.equals("R")) {
                return new MatchOperation(true, false, "");
            }

            return new MatchOperation(false, false, s);
        }
    }

    record Rule(char index, char operation, Integer ruleValue, MatchOperation matchOperation) {
        public static Rule of(String s) {
            String[] data = s.split(":");
            MatchOperation operation = MatchOperation.of(data[1]);
            Integer partValue = Integer.valueOf(data[0].substring(2), 10);
            return new Rule(data[0].charAt(0), data[0].charAt(1), partValue, operation);
        }

        public boolean match(Part p) {
            boolean match = false;
            int partValue = p.values.get(index);
            switch (operation) {
                case '>' -> match = partValue > ruleValue;
                case '<' -> match = partValue < ruleValue;
            }

            return match;
        }

        public Optional<PartInterval> partThatMatch(PartInterval p) {
            return partThatMatchForOperation(p, operation, 1);
        }

        public Optional<PartInterval> partThatDoesNotMatch(PartInterval p) {
            return partThatMatchForOperation(p, operation == '>' ? '<' : '>', 0);
        }

        private Optional<PartInterval> partThatMatchForOperation(PartInterval p, char localOperation, int offset) {
            Tuple<Integer> partValue = p.values.get(index);
            Tuple<Integer> matchValue = null;
            if (localOperation == '<' && ruleValue > partValue.start) {
                matchValue = new Tuple<>(partValue.start, Math.min(ruleValue - offset, partValue.end));
            }

            if (localOperation == '>' && ruleValue < partValue.end()) {
                matchValue = new Tuple<>(Math.max(ruleValue + offset, partValue.start), partValue.end);
            }

            if (matchValue != null) {
                PartInterval matching = new PartInterval(new HashMap<>(Map.copyOf(p.values)));
                matching.values.put(index, matchValue);
                return Optional.of(matching);
            }

            return Optional.empty();
        }
    }

    record SimulationStep(PartInterval partInterval, String workflowName) {}

    record Part(Map<Character, Integer> values) {
        public static Part of(String s) {
            String tmp = s.substring(1, s.length() - 1);
            String[] data = tmp.split(",");
            Map<Character, Integer> part = new HashMap<>();
            for (String value: data) {
                String[] valueSplit = value.split("=");
                part.put(valueSplit[0].charAt(0), Integer.valueOf(valueSplit[1], 10));
            }

            return new Part(part);
        }

        public long getValue() {
            return values.values().stream().map(Long::valueOf).reduce(0L, Long::sum);
        }
    }

    record Tuple<T>(T start, T end) {}
    record PartInterval(Map<Character, Tuple<Integer>> values) {
        public long getValue() {
            return values.values().stream().map(t -> t.end * (t.end + 1L) / 2 - t.start * (t.start + 1L) / 2).reduce(0L, Long::sum);
        }

        public long combinations() {
            return values.values().stream().map(t -> t.end - t.start + 1L).reduce(1L, (a, b) -> a * b);
        }
    }

    record Workflow(List<Rule> rules, MatchOperation matchOperation) {
        public MatchOperation match(Part p) {
            for (Rule r: rules) {
                if (r.match(p)) {
                    return r.matchOperation;
                }
            }

            return matchOperation;
        }
    }

    public long solve(List<String> input) {
        List<Part> parts = new ArrayList<>();
        Map<String, Workflow> workflows = new HashMap<>();
        parseInput(input, parts, workflows);

        List<Part> acceptedParts = parts.stream().filter(p -> isAccepted(p, workflows)).toList();
        long value = 0;
        for (Part p: acceptedParts) {
            value += p.getValue();
        }

        return value;
    }

    public long solve2(List<String> input) {
        List<Part> parts = new ArrayList<>();
        Map<String, Workflow> workflows = new HashMap<>();
        parseInput(input, parts, workflows);

        PartInterval startingIntervals = new PartInterval(Map.of(
            'x', new Tuple<>(1, 4000),
            'm', new Tuple<>(1, 4000),
            'a', new Tuple<>(1, 4000),
            's', new Tuple<>(1, 4000)
        ));

        List<PartInterval> acceptedParts = computeMatchingPartIntervals(startingIntervals, workflows);
        long value = 0;
        for (PartInterval p: acceptedParts) {
            value += p.combinations();
        }

        return value;
    }

    private List<PartInterval> computeMatchingPartIntervals(PartInterval initial, Map<String, Workflow> workflows) {
        List<PartInterval> accepted = new ArrayList<>();

        Queue<SimulationStep> toVisit = new LinkedList<>();
//        Set<PartInterval> visited = new HashSet<>();

//        visited.add(initial);
        toVisit.add(new SimulationStep(initial, "in"));
        while(!toVisit.isEmpty()) {
            SimulationStep step = toVisit.poll();
            PartInterval current = step.partInterval;
            Workflow workflow = workflows.get(step.workflowName);

            for (Rule r: workflow.rules) {
                Optional<PartInterval> matching = r.partThatMatch(current);
                Optional<PartInterval> notMatching = r.partThatDoesNotMatch(current);

                if (matching.isPresent()) {
                    MatchOperation matchOperation = r.matchOperation;
                    if (!matchOperation.terminal) {
                        toVisit.add(new SimulationStep(matching.get(), matchOperation.nextWorkflow));
                    } else if (matchOperation.accepted) {
                        accepted.add(matching.get());
                    }
                }

                current = notMatching.orElse(null);
                if (current == null) {
                    break;
                }
            }

            if (current != null) {
                MatchOperation matchOperation = workflow.matchOperation;
                if (!matchOperation.terminal) {
                    toVisit.add(new SimulationStep(current, matchOperation.nextWorkflow));
                } else if (matchOperation.accepted) {
                    accepted.add(current);
                }
            }
        }

        return accepted;
    }

    private boolean isAccepted(Part p, Map<String, Workflow> workflows) {
        Workflow current = workflows.get("in");
        MatchOperation currentMatch = current.match(p);
        while(!currentMatch.terminal) {
            current = workflows.get(currentMatch.nextWorkflow);
            currentMatch = current.match(p);
        }

        return currentMatch.accepted;
    }

    private void parseInput(List<String> input, List<Part> parts, Map<String, Workflow> workflows) {
        boolean matchParts = false;
        for (String s: input) {
            if (s.isEmpty()) {
                matchParts = true;
            } else if (matchParts) {
                parts.add(Part.of(s));
            } else {
                List<Rule> rules = new ArrayList<>();
                int nameEnd = s.indexOf("{");
                String name = s.substring(0, nameEnd);
                String content = s.substring(nameEnd + 1, s.length() - 1);
                String[] rulesStrings = content.split(",");
                for (int i = 0; i < rulesStrings.length - 1; i++) {
                    String ruleString = rulesStrings[i];
                    rules.add(Rule.of(ruleString));
                }
                MatchOperation operation = MatchOperation.of(rulesStrings[rulesStrings.length - 1]);

                workflows.put(name, new Workflow(rules, operation));
            }
        }
    }
}
