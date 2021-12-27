package com.gianlucadurelli.coding.realworld;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class MadgeRemoveUnused {
	// Given a list of module dependencies as given by Madge tool (), and a list of entry points
	// Detect all the modules not reacheable from the entry points
	public Set<String> computeNotUsed(String dependenciesFile, String entryPointsFile, String inversifyDependencies) throws IOException {
		Map<String, Set<String>> graph = buildModuleDepencencyGraph(dependenciesFile);
		Set<String> entryPoints = extractEntryPoints(entryPointsFile);
		processInversifyDependencies(inversifyDependencies, graph);

		Set<String> reacheableModules = new HashSet<>();
		for (String module: entryPoints) {
			bfs(module, reacheableModules, graph);
		}

		Set<String> notReacheableModules = new HashSet<>(graph.keySet());
		notReacheableModules.removeAll(reacheableModules);

		return notReacheableModules;
	}

	private void bfs(String module, Set<String> reacheableModules, Map<String, Set<String>> graph) {
		Queue<String> toVisit = new LinkedList<>();
		if (!reacheableModules.contains(module)) {
			toVisit.add(module);

			while(!toVisit.isEmpty()) {
				String current = toVisit.remove();

				Set<String> adjacents = graph.getOrDefault(current, new HashSet<>());
				for (String next: adjacents) {
					if (!reacheableModules.contains(next)) {
						toVisit.add(next);
						reacheableModules.add(next);
					}
				}
			}
		}
	}

	private Set<String> extractEntryPoints(String entryPointsFile) throws IOException {
		Set<String> entryPoints = new HashSet<>();
		try (Stream<String> stream = Files.lines(Paths.get(entryPointsFile))) {
			stream.forEach(entryPoints::add);
		}

		return entryPoints;
	}

	private Map<String, Set<String>> buildModuleDepencencyGraph(String dependencies) throws IOException {
		Map<String, Set<String>> graph = new HashMap<>();
		AtomicReference<String> lastNode = new AtomicReference<>("");
		try (Stream<String> stream = Files.lines(Paths.get(dependencies))) {
			stream.forEach(s -> {
				boolean isNode = s.charAt(0) != ' ';
				if (isNode) {
					graph.put(s, graph.getOrDefault(s, new HashSet<>()));
					lastNode.set(s);
				} else {
					String normalized = s.trim();
					graph.put(normalized, graph.getOrDefault(normalized, new HashSet<>()));
					graph.get(lastNode.get()).add(normalized);
				}
			});
		}

		return graph;
	}

	private void processInversifyDependencies(String inversifyDependencies, Map<String, Set<String>> graph) throws IOException {
		AtomicReference<String> lastNode = new AtomicReference<>("");
		try (Stream<String> stream = Files.lines(Paths.get(inversifyDependencies))) {
			stream.forEach(s -> {
				boolean isNode = s.charAt(0) != ' ';
				if (isNode && graph.containsKey(s)) {
					graph.put(s, graph.getOrDefault(s, new HashSet<>()));
					lastNode.set(s);
				} else {
					String normalized = s.trim();
					if (graph.containsKey(lastNode.get()) && graph.containsKey(normalized)) {
						graph.put(normalized, graph.getOrDefault(normalized, new HashSet<>()));
						graph.get(lastNode.get()).add(normalized);
					}
				}
			});
		}
	}
}
