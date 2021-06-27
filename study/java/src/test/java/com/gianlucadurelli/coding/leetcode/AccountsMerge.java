package com.gianlucadurelli.coding.leetcode;

import java.util.*;
import java.util.stream.Collectors;

// https://leetcode.com/explore/interview/card/facebook/52/trees-and-graphs/3027
public class AccountsMerge {
	private class Node {
		String name;
		String email;
		Set<String> edges;

		public Node(String name, String email, Set<String> edges) {
			this.name = name;
			this.email = email;
			this.edges = edges;
		}
	}

	public List<List<String>> accountsMerge(List<List<String>> accounts) {
		Map<String, Node> nodes = new HashMap<>();

		for (List<String> account: accounts) {
			String name = account.get(0);
			List<String> mails = account.stream().skip(1).collect(Collectors.toList());
			for (String s: mails) {
				if (!nodes.containsKey(s)) {
					Node n = new Node(name, s, new HashSet<>());
					nodes.put(s, n);
				}

				Node n = nodes.get(s);
				n.edges.addAll(mails);
			}
		}

		List<List<String>> result = new LinkedList<>();

		Set<String> visited = new HashSet<>();
		Queue<String> toVisit = new LinkedList<>(nodes.keySet());
		while(!toVisit.isEmpty()) {
			String current = toVisit.remove();
			if (!visited.contains(current)) {
				visited.add(current);
				Node n = nodes.get(current);

				Set<String> connectedMails = connectedComponents(current, nodes, visited);
				List<String> account = new LinkedList<>();
				account.add(n.name);
				account.addAll(connectedMails.stream().sorted().collect(Collectors.toList()));
				result.add(account);
			}
		}

		return result;
	}

	private Set<String> connectedComponents(String mail, Map<String, Node> nodes, Set<String> visited) {
		Node n = nodes.get(mail);

		Set<String> connected = new HashSet<>();
		connected.add(mail);
		for (String m: n.edges) {
			if (!visited.contains(m)) {
				visited.add(m);
				connected.addAll(connectedComponents(m, nodes, visited));
			}
		}

		return connected;
	}
}
