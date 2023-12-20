package com.gianlucadurelli.coding.adventofcode.year2023;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day20PulsePropagation {
    private record GeneratedPulses(long highCount, long lowCount) {}
    private record Pulse(String source, String destination, boolean isHigh) {}
    private interface Module {
        Optional<Boolean> processPulse(Pulse pulse);
        String getName();
        Module copy();
    }

    @Data
    @Getter
    @EqualsAndHashCode
    @AllArgsConstructor
    private static class FlipFlop implements Module {
        String name;
        boolean isOn;

        @Override
        public Optional<Boolean> processPulse(Pulse pulse) {
            if (pulse.isHigh) {
                return Optional.empty();
            }

            isOn = !isOn;
            return Optional.of(isOn);
        }

        @Override
        public Module copy() {
            return new FlipFlop(name, isOn);
        }
    }

    @Data
    @Getter
    @EqualsAndHashCode
    @AllArgsConstructor
    private static class Conjuction implements Module {
        String name;
        Map<String, Boolean> inputsStatus;

        @Override
        public Optional<Boolean> processPulse(Pulse pulse) {
            inputsStatus.put(pulse.source, pulse.isHigh);
            boolean result = inputsStatus.values().stream().anyMatch(v -> !v);
            return Optional.of(result);
        }

        @Override
        public Module copy() {
            return new Conjuction(name, new HashMap<>(Map.copyOf(inputsStatus)));
        }
    }

    private record Broadcast(String name) implements Module {
        @Override
        public Optional<Boolean> processPulse(Pulse pulse) {
            return Optional.of(pulse.isHigh);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Module copy() {
            return new Broadcast(name);
        }
    }

    private record Cables(Map<String, List<String>> connetions) {}

    private record Machines(Map<String, Module> modules, Cables cablesFrom, Cables cablesTo) {}

    public long solve(List<String> input) {
        Machines machines = parseInput(input);

        Map<Set<Module>, Integer> visitedConfigurations = new HashMap<>();
        visitedConfigurations.put(copyModules(machines.modules.values()), 0);
        Map<Integer, GeneratedPulses> generatedPulsesMap = new HashMap<>();
        generatedPulsesMap.put(0, new GeneratedPulses(0, 0));

        int loopAt = -1;
        int buttonPressCount = 0;
        while (buttonPressCount < 1000) {
            buttonPressCount++;

            GeneratedPulses pulses = simulateButtonPress(machines, p -> true);
            Set<Module> newConfiguration = copyModules(machines.modules.values());
            if (visitedConfigurations.containsKey(newConfiguration)) {
                loopAt = visitedConfigurations.get(newConfiguration);
                visitedConfigurations.put(newConfiguration, buttonPressCount);
                generatedPulsesMap.put(buttonPressCount, pulses);
                break;
            }

            visitedConfigurations.put(newConfiguration, buttonPressCount);
            generatedPulsesMap.put(buttonPressCount, pulses);
        }

        if (loopAt == -1) {
            return computePulses(generatedPulsesMap, 0, 1000);
        }

        int loopLength = buttonPressCount - loopAt;
        int beforeLoop = loopAt;
        int loopIterations = (1000 - beforeLoop) / loopLength;
        int afterLoop = 1000 - (beforeLoop + loopIterations * loopLength);

        System.out.println("Looped at: " + loopAt);
        System.out.println("Loop length: " + loopLength);
        System.out.println("Loop iterations: " + loopIterations);
        System.out.println("Before loop: " + beforeLoop);
        System.out.println("After loop: " + beforeLoop);
        System.out.println("Check = 1000? " + (beforeLoop + loopLength * loopIterations + afterLoop));

        return computePulses(generatedPulsesMap, 0, beforeLoop)
                + computePulses(generatedPulsesMap, loopAt, loopAt + loopLength) * loopIterations * loopIterations
                + computePulses(generatedPulsesMap, loopAt, loopAt + afterLoop);
    }

    public long solve2(List<String> input, List<String> interestingNodes) {
        List<Long> loops = interestingNodes.stream()
                .map(node -> findSingleLow(input, p -> p.destination.equals(node) && !p.isHigh))
                .toList();
        long solution = loops.get(0);
        return loops.stream().reduce(solution, this::lcm);
    }

    public long findSingleLow(List<String> input, Predicate<Pulse> pulsePredicate) {
        Machines machines = parseInput(input);

        long buttonPressCount = 0;
        while (buttonPressCount < 100000000) {
            if (buttonPressCount % 1000000 == 0) {
                System.out.println("Simulating: " + buttonPressCount);
            }
            buttonPressCount++;

            GeneratedPulses pulses = simulateButtonPress(machines, pulsePredicate);
            if (pulses.lowCount == 1) {
                return buttonPressCount;
            }
        }

        return -1;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        long t;
        while (b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private long computePulses(Map<Integer, GeneratedPulses> generatedPulsesMap, int from, int to) {
        long low = 0;
        long high = 0;
        for (int i = from + 1; i <= to; i++) {
            GeneratedPulses p = generatedPulsesMap.get(i);
            low += p.lowCount;
            high += p.highCount;
        }

        return low * high;
    }

    public GeneratedPulses simulateButtonPress(Machines machines, Predicate<Pulse> pulseMatcher) {
        int highCount = 0;
        int lowCount = 0;

        Queue<Pulse> toVisit = new LinkedList<>();

        Pulse initial = new Pulse("button", "broadcaster", false);
        if (pulseMatcher.test(initial)) {
            lowCount++;
        }
        toVisit.add(initial);

        while(!toVisit.isEmpty()) {
            Pulse pulse = toVisit.poll();
            String targetName = pulse.destination;

            if (!machines.modules.containsKey(targetName)) {
                // Goes to unknown module
                continue;
            }

            Module target = machines.modules.get(targetName);
            Optional<Boolean> forwardSignal = target.processPulse(pulse);

            if (forwardSignal.isPresent()) {
                boolean nextPulse = forwardSignal.get();

                List<String> destinations = machines.cablesFrom.connetions.getOrDefault(targetName, Collections.emptyList());
                for (String d: destinations) {
                    Pulse newPulse = new Pulse(targetName, d, nextPulse);
                    if (pulseMatcher.test(newPulse)) {
                        if (nextPulse) {
                            highCount++;
                        } else {
                            lowCount++;
                        }
                    }
                    toVisit.add(newPulse);
                }
            }
        }

        return new GeneratedPulses(highCount, lowCount);
    }

    private Set<Module> copyModules(Collection<Module> modules) {
        return modules.stream().map(Module::copy).collect(Collectors.toSet());
    }

    private Machines parseInput(List<String> input) {
        Cables from = new Cables(new HashMap<>());
        Cables to = new Cables(new HashMap<>());
        Map<String, Module> modules = new HashMap<>();

        for (String s: input) {
            String[] data = s.split(" -> ");
            String name = getModuleName(data[0]);
            List<String> connections = Arrays.stream(data[1].split(", ")).toList();

            from.connetions.put(name, connections);
            for (String conn: connections) {
                List<String> fromConnections = to.connetions().getOrDefault(conn, new ArrayList<>());
                fromConnections.add(name);
                to.connetions.put(conn, fromConnections);
            }
        }

        for (String s: input) {
            Module m = createModule(s, to);
            modules.put(m.getName(), m);
        }

        return new Machines(modules, from, to);
    }

    private String getModuleName(String s) {
        if (s.equals("broadcaster")) {
            return s;
        }

        return s.substring(1);
    }

    private char getModuleType(String s) {
        if (s.equals("broadcaster")) {
            return 'b';
        }

        return s.charAt(0);
    }

    private Module createModule(String s, Cables to) {
        String[] data = s.split(" -> ");
        String name = getModuleName(data[0]);
        char type = getModuleType(data[0]);

        if (type == 'b') {
            return new Broadcast(name);
        }

        if (type == '%') {
            return new FlipFlop(name, false);
        }

        if (type == '&') {
            List<String> fromConnections = to.connetions.get(name);
            Map<String, Boolean> values = new HashMap<>();
            fromConnections.forEach(n -> values.put(n, false));
            return new Conjuction(name, values);
        }

        throw new RuntimeException("Unknown type " + type);
    }
}
