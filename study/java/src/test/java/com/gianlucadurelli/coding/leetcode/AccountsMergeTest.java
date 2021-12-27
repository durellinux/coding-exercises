package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountsMergeTest {

	@Test
	public void test() {
		AccountsMerge solver = new AccountsMerge();

		Assertions.assertThat(solver.accountsMerge(List.of(
				List.of("John","johnsmith@mail.com","john_newyork@mail.com"),
				List.of("John","johnsmith@mail.com","john00@mail.com"),
				List.of("Mary","mary@mail.com"),
				List.of("John","johnnybravo@mail.com")
		))).containsExactlyInAnyOrderElementsOf(
				List.of(
						List.of("John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"),
						List.of("Mary","mary@mail.com"),
						List.of("John","johnnybravo@mail.com")
				)
		);

		Assertions.assertThat(solver.accountsMerge(List.of(
				List.of("Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"),
				List.of("Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"),
				List.of("Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"),
				List.of("Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"),
				List.of("Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co")
		))).containsExactlyInAnyOrderElementsOf(
				List.of(
						List.of("Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"),
						List.of("Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"),
						List.of("Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"),
						List.of("Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"),
						List.of("Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co")
				)
		);
	}

}