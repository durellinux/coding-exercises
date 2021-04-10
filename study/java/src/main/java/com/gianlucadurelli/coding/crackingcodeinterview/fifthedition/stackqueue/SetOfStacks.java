package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.stackqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SetOfStacks<T> {
    protected int threshold;
    protected int currentStack = -1;
    protected List<Stack<T>> stacks;
    protected List<Integer> elements;

    public SetOfStacks(int threshold) {
        this.threshold = threshold;
        this.stacks = new ArrayList<>();
        this.elements = new ArrayList<>();
    }

    public T pop() throws Exception {
        if (currentStack < 0) {
            throw new Exception("Stack is empty");
        }

        T value = stacks.get(currentStack).pop();
        elements.set(currentStack, elements.get(currentStack) - 1);
        if (stacks.get(currentStack).isEmpty()) {
            stacks.remove(currentStack);
            elements.remove(currentStack);
            currentStack -= 1;
        }

        return value;
    }

    public void push(T value) {
        if (currentStack < 0) {
            currentStack = 0;
            stacks.add(new Stack<>());
            elements.add(0);
        }

        stacks.get(currentStack).push(value);
        elements.set(currentStack, elements.get(currentStack) + 1);

        if (elements.get(currentStack).equals(threshold)) {
            currentStack += 1;
            stacks.add(new Stack<>());
            elements.add(0);
        }
    }

    private void addStack() {

    }

    private void removeStack() {

    }

    public int getNumberOfStacks() {
        return stacks.size();
    }

}
