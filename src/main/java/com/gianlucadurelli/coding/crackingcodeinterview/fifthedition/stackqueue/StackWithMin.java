package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.stackqueue;

import java.util.Stack;

public class StackWithMin<T extends Comparable> extends Stack<T> {
    private Stack<T> minStack;

    public StackWithMin() {
        minStack = new Stack<>();
    }

    @Override
    public T push(T item) {
        if (minStack.empty() || item.compareTo(minStack.peek()) < 0) {
            minStack.push(item);
        } else {
            minStack.push(minStack.peek());
        }
        return super.push(item);
    }

    @Override
    public synchronized T pop() {
        minStack.pop();
        T value = super.pop();
        return value;
    }

    public T getMin() {
        if (minStack.isEmpty()) {
            return null;
        }

        return minStack.peek();
    }
}
