package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutismScreening {
    public static String findOdd(List<String> series) {
        Map<List<Integer>, List<String>> data = new HashMap<>();

        for (String s: series) {
            List<Integer> eval = new ArrayList<>();
            for (int i = 1;  i < s.length(); i++) {
                eval.add(s.charAt(i) - s.charAt(i-1));
            }

            if (!data.containsKey(eval)) {
                data.put(eval, new ArrayList<>());
            }

            data.get(eval).add(s);
        }

        return data.values().stream()
                .filter(l -> l.size() == 1)
                .map(l -> l.get(0))
                .findFirst().orElseThrow(() -> new RuntimeException("Failed to compute odd one"));
    }
}
