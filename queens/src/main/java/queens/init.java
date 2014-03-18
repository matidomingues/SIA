package queens;

import gps.SearchStrategy;

public class init {

	public static void main(String[] args) {
		queensGPSProblem problem = new queensGPSProblem();
		queensGPSEngine engine = new queensGPSEngine();
		engine.engine(problem, SearchStrategy.DFS);
	}

}
