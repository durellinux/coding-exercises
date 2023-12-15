package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Day15LensLibrary {

    private record Data(String label, int number) {};

    private static class DequeueNode {
        DequeueNode prev = null;
        DequeueNode next = null;
        Data data;

        public DequeueNode(DequeueNode prev, DequeueNode next, Data data) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }
    }

    private record Box(Map<String, DequeueNode> map, DequeueNode head, DequeueNode tail) {
        void apply(String command) {
            if (command.contains("-")) {
                this.remove(command.split("-")[0]);
            } else {
                String[] values = command.split("=");
                this.add(new Data(values[0], Integer.valueOf(values[1], 10)));
            }
        }

        void remove(String label) {
            if (map.containsKey(label)) {
                DequeueNode node = map.get(label);
                DequeueNode prev = node.prev;
                DequeueNode next = node.next;
                prev.next = next;
                next.prev = prev;
                map.remove(label);
            }
        }
        void add(Data data) {
            if (map.containsKey(data.label)) {
                DequeueNode node = map.get(data.label);
                node.data = data;
            } else {
                DequeueNode tail = tail();
                DequeueNode beforeTail = tail.prev;
                DequeueNode node = new DequeueNode(beforeTail, tail, data);
                beforeTail.next = node;
                tail.prev = node;
                map.put(data.label, node);
            }
        }

        long computeValue() {
            long value = 0;
            long position = 0;
            DequeueNode node = head;
            while(node != null) {
                value += position * node.data.number;
                position++;
                node = node.next;
            }

            return value;
        }
    }

    public long solve(String input) {
        String[] commands = input.split(",");
        long result = 0;
        for (String c: commands) {
            result += hash(c);
        }

        return result;
    }

    public long solve2(String input) {
        String[] commands = input.split(",");
        Map<Integer, Box> boxes = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            DequeueNode head = new DequeueNode(null, null, new Data("", 0));
            DequeueNode tail = new DequeueNode(null, null, new Data("", 0));
            head.next = tail;
            tail.prev = head;
            boxes.put(i, new Box(new HashMap<>(), head, tail));
        }

        for (String c: commands) {
            String label = c.split("[-=]")[0];
            int commandHash = hash(label);
            Box box = boxes.get(commandHash);
            box.apply(c);
        }

        long value = 0;
        for (int i = 0; i < 256; i++) {
            Box box = boxes.get(i);
            value += (i + 1) * box.computeValue();
        }

        return value;
    }

    public int hash(String input) {
        int currentValue = 0;
        for (char c: input.toCharArray()) {
            currentValue += c;
            currentValue *= 17;
            currentValue = currentValue % 256;
        }

        return currentValue;
    }
}
