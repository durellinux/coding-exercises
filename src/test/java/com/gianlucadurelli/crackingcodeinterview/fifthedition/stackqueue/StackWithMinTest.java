package com.gianlucadurelli.crackingcodeinterview.fifthedition.stackqueue;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class StackWithMinTest {

    // TODO: Rename test with exercise name
    @Test
    public void testMinValue() {

        StackWithMin<Integer> myStack = new StackWithMin<>();

        Assertions.assertThat(myStack.getMin()).isNull();

        myStack.push(5);
        Assertions.assertThat(myStack.getMin()).isEqualTo(5);

        myStack.push(3);
        Assertions.assertThat(myStack.getMin()).isEqualTo(3);


        myStack.push(5);
        Assertions.assertThat(myStack.getMin()).isEqualTo(3);

        myStack.pop();
        Assertions.assertThat(myStack.getMin()).isEqualTo(3);

        myStack.pop();
        //Assertions.assertThat(myStack.getMin()).isNull();
    }

}