package gps;

import java.util.HashMap;
import java.util.Map;

public enum SearchStrategy {
	BFS("BFS"),
	DFS("DFS"),
	AStar("ASTAR"),
    IDDFS("IDDFS"),
    Greedy("GREEDY");

	private static final Map<String, SearchStrategy> translatorMap;
	private final String name;

	static {
		translatorMap = new HashMap<String, SearchStrategy>();
		translatorMap.put("BFS", BFS);
		translatorMap.put("DFS", DFS);
		translatorMap.put("IDDFS", IDDFS);
		translatorMap.put("ASTAR", AStar);
		translatorMap.put("GREEDY", Greedy);
	}

	public static SearchStrategy getSearchStrategy(String name) {
		return translatorMap.get(name.toUpperCase());
	}

	private SearchStrategy(String name) {
		this.name = name;
	}
}
