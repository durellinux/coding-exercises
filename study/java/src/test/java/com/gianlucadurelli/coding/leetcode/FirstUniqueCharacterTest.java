package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FirstUniqueCharacterTest {
    @Test
    public void test() {
        FirstUniqueCharacter solver = new FirstUniqueCharacter();
        Assertions.assertThat(solver.firstUniqChar("leetcode")).isEqualTo(0);
        Assertions.assertThat(solver.firstUniqChar("loveleetcode")).isEqualTo(2);
        Assertions.assertThat(solver.firstUniqChar("aabb")).isEqualTo(-1);
    }
}