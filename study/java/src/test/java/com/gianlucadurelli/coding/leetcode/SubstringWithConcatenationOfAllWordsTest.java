package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class SubstringWithConcatenationOfAllWordsTest {

    @Test
    public void example1() {
        SubstringWithConcatenationOfAllWords solver = new SubstringWithConcatenationOfAllWords();
        Assertions.assertThat(
                solver.findSubstring("barfoothefoobarman", new String[]{"foo","bar"})
        ).isEqualTo(List.of(0,9));
    }

    @Test
    public void example2() {
        SubstringWithConcatenationOfAllWords solver = new SubstringWithConcatenationOfAllWords();
        Assertions.assertThat(
                solver.findSubstring("wordgoodgoodgoodbestword", new String[]{"word","good","best","word"})
        ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void example3() {
        SubstringWithConcatenationOfAllWords solver = new SubstringWithConcatenationOfAllWords();
        Assertions.assertThat(
                solver.findSubstring("barfoofoobarthefoobarman", new String[]{"bar","foo","the"})
        ).isEqualTo(List.of(6,9,12));
    }

    @Test
    public void failed1() {
        SubstringWithConcatenationOfAllWords solver = new SubstringWithConcatenationOfAllWords();
        Assertions.assertThat(
                solver.findSubstring("lingmindraboofooowingdingbarrwingmonkeypoundcake", new String[]{"fooo","barr","wing","ding","wing"})
        ).isEqualTo(List.of(13));
    }

    @Test
    public void failed2() {
        SubstringWithConcatenationOfAllWords solver = new SubstringWithConcatenationOfAllWords();
        Assertions.assertThat(
                solver.findSubstring("wordgoodgoodgoodbestword", new String[]{"word","good","best","good"})
        ).isEqualTo(List.of(8));
    }

    @Test
    public void failed3() {
        SubstringWithConcatenationOfAllWords solver = new SubstringWithConcatenationOfAllWords();
        Assertions.assertThat(
                solver.findSubstring("aaaaaaaaaaaaaa", new String[]{"aa","aa"})
        ).isEqualTo(List.of(0,1,2,3,4,5,6,7,8,9,10));
    }
}