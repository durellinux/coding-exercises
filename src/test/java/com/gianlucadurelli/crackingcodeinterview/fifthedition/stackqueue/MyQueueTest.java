package com.gianlucadurelli.crackingcodeinterview.fifthedition.stackqueue;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MyQueueTest {

    // TODO: Rename test with exercise name
    @Test
    public void testClass() {
        int start = 0;
        int end = 1000;

        MyQueue<Integer> myQueue = new MyQueue<>();

        for (int i = start; i<end; i++) {
            myQueue.enqueue(i);
        }

        for (int i = start; i<end; i++) {
            int v = myQueue.dequeue();
            Assertions.assertThat(v).isEqualTo(i);
        }
    }
}