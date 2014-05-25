package ar.edu.itba.sia.genetics.utils;

import java.util.Comparator;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;

public class FenotypeComparator implements Comparator<Fenotype> {

	private final FitnessFunction fitnessFunction;

	public FenotypeComparator(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	public int compare(Fenotype o1, Fenotype o2) {
		
		return (int)(fitnessFunction.evaluate(o1)-fitnessFunction.evaluate(o2));
	}

}
