package com.gianlucadurelli.coding.leetcode;

import java.util.*;
import java.util.stream.Collectors;

// https://leetcode.com/explore/interview/card/facebook/52/trees-and-graphs/3027
public class AccountsMerge {

	public List<List<String>> accountsMerge(List<List<String>> accounts) {
		Map<String, Set<String>> nodes = new HashMap<>();
		Map<String, String> mailToName = new HashMap<>();

		for (List<String> account: accounts) {
			String name = account.get(0);
			Set<String> mails = account.stream().skip(1).collect(Collectors.toSet());
			for (String s: mails) {
				if (!nodes.containsKey(s)) {
					mailToName.put(s, name);
					nodes.put(s, new HashSet<>());
				}

				nodes.get(s).addAll(mails);
			}


		}

		List<List<String>> result = new LinkedList<>();

		Set<String> visited = new HashSet<>();
		for (String current: nodes.keySet()) {
			if (!visited.contains(current)) {
				Set<String> connectedMails = new HashSet<>();
				Stack<String> stack = new Stack<>();
				stack.push(current);
				visited.add(current);

				while(!stack.empty()) {
					String node = stack.pop();
					connectedMails.add(node);

					for (String otherMail: nodes.get(node)) {
						if (!visited.contains(otherMail)) {
							visited.add(otherMail);
							stack.push(otherMail);
						}
					}
				}

				List<String> account = new LinkedList<>();
				account.add(mailToName.get(current));
				account.addAll(connectedMails.stream().sorted().collect(Collectors.toList()));
				result.add(account);
			}
		}

		return result;
	}
}
