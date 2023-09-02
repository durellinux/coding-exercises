package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TextJustificationTest {

    private static final TextJustification solver = new TextJustification();

    @Test
    public void example1() {
        Assertions.assertThat(solver.fullJustify(
                        new String[]{"This", "is", "an", "example", "of", "text", "justification."},
                        16
                ))
                .isEqualTo(List.of(
                        "This    is    an",
                        "example  of text",
                        "justification.  "
                ));
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.fullJustify(new String[]{"What","must","be","acknowledgment","shall","be"}, 16))
                .isEqualTo(List.of(
                        "What   must   be",
                        "acknowledgment  ",
                        "shall be        "
                ));
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.fullJustify(
                new String[]{"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"},
                        20
                ))
                .isEqualTo(List.of(
                        "Science  is  what we",
                        "understand      well",
                        "enough to explain to",
                        "a  computer.  Art is",
                        "everything  else  we",
                        "do                  "
                ));
    }

    @Test
    public void testCase1() {
        Assertions.assertThat(solver.fullJustify(
                        new String[]{"Listen","to","many,","speak","to","a","few."},
                        6
                ))
                .isEqualTo(List.of(
                        "Listen",
                        "to    ",
                        "many, ",
                        "speak ",
                        "to   a",
                        "few.  "
                ));
    }

}