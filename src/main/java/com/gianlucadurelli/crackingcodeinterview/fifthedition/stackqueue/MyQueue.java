package com.gianlucadurelli.crackingcodeinterview.fifthedition.stackqueue;

import java.util.Stack;

public class MyQueue<T> {
    private Stack<T> enqueueStack;
    private Stack<T> helperStack;

    public MyQueue() {
        enqueueStack = new Stack<>();
        helperStack = new Stack<>();
    }

    public void enqueue(T value) {
        enqueueStack.push(value);
    }

    public T dequeue() {
        while (!enqueueStack.isEmpty()) {
            T value = enqueueStack.pop();
            helperStack.push(value);
        }

        T result = helperStack.pop();

        while (!helperStack.isEmpty()) {
            T value = helperStack.pop();
            enqueueStack.push(value);
        }

        return result;
    }
}
