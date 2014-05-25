package ar.edu.itba.sia.genetics.utils;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CachingFenotypeComparator implements Comparator<Fenotype> {

	private final FitnessFunction fitnessFunction;
	private final Map<Fenotype, Double> cachedFitness;

	public CachingFenotypeComparator(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		this.cachedFitness = new HashMap<Fenotype, Double>();
	}

	@Override
	public int compare(Fenotype o1, Fenotype o2) {
		Double f1, f2;
		if ((f1 = cachedFitness.get(o1)) == null) {
			f1 = fitnessFunction.evaluate(o1);
			cachedFitness.put(o1, f1);
		}
		if ((f2 = cachedFitness.get(o2)) == null) {
			f2 = fitnessFunction.evaluate(o2);
			cachedFitness.put(o2, f2);
		}
		int result = (int)Math.signum(f1 - f2);
		return result;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
