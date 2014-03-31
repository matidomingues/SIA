package deeptrip.ai.enumerators;

import deeptrip.ai.heuristics.Heuristic;
import deeptrip.ai.heuristics.HeuristicOne;
import deeptrip.ai.heuristics.HeuristicThree;
import deeptrip.ai.heuristics.HeuristicTwo;

import java.util.HashMap;
import java.util.Map;

public enum HeuristicType {
	ONE(new HeuristicOne(), "ONE"),
	TWO(new HeuristicTwo(), "TWO"),
	THREE(new HeuristicThree(), "THREE");

	private static final Map<String, HeuristicType> translatorMap;
	private final Heuristic heuristic;
	private final String name;

	static {
		translatorMap = new HashMap<>();
		translatorMap.put("ONE", ONE);
		translatorMap.put("TWO", TWO);
		translatorMap.put("THREE", THREE);
	}

	public static HeuristicType getHeuristicType(String name) {
		return translatorMap.get(name.toUpperCase());
	}

	private HeuristicType(Heuristic heuristic, String name) {
		this.heuristic = heuristic;
		this.name = name;
	}

	public Heuristic getHeuristic() {
		return this.heuristic;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
