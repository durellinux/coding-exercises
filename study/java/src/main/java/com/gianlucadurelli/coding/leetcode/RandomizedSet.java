package com.gianlucadurelli.coding.leetcode;

import java.util.*;

// https://leetcode.com/problems/insert-delete-getrandom-o1/?envType=study-plan-v2&envId=top-interview-150
public class RandomizedSet {
    Map<Integer, Integer> valueMap = new HashMap<>();
    int[] valueArray = new int[100000];
    int insertPosition = 0;

    public RandomizedSet() {

    }

    public boolean insert(int val) {
        if (valueMap.containsKey(val)) {
            return false;
        }

        int arrayPosition = insertPosition;
        valueArray[arrayPosition] = val;
        valueMap.put(val, arrayPosition);

        insertPosition++;

        return true;
    }

    public boolean remove(int val) {
        if (!valueMap.containsKey(val)) {
            return false;
        }

        int oldPosition = valueMap.get(val);

        if (insertPosition == 1) {
            insertPosition = 0;
        } else {
            int lastPosition = insertPosition - 1;
            int lastElement = valueArray[lastPosition];

            valueArray[oldPosition] = lastElement;
            valueMap.put(lastElement, oldPosition);
            insertPosition--;
        }

        valueMap.remove(val);

        return true;
    }

    public int getRandom() {
        int randomPosition = Double.valueOf(Math.random() * insertPosition).intValue();
        return valueArray[randomPosition];
    }
}