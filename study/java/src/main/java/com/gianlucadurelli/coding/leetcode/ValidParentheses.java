package com.gianlucadurelli.coding.leetcode;

import java.util.LinkedList;
import java.util.Stack;

// https://leetcode.com/explore/interview/card/amazon/76/array-and-strings/2972/
public class ValidParentheses {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();

        for (char c: chars) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                char toBeClosed = stack.pop();
                if (c == ')' && toBeClosed != '(') {
                    return false;
                }

                if (c == ']' && toBeClosed != '[') {
                    return false;
                }

                if (c == '}' && toBeClosed != '{') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
