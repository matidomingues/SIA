package ar.edu.itba.sia.genetics.cutcondition.impl;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContentCutCondition implements CutCondition {

	private final FitnessFunction fitnessFunction;
	private final Comparator<Fenotype> comparator;
	private final double epsilon;
	private final int haltedMaxTimes;
	private double fitness;
	private int haltedTimes;

	public ContentCutCondition(int haltedMaxTimes, double epsilon, FitnessFunction finFitnessFunction,
							   Comparator<Fenotype> comparator) {
		this.haltedTimes=0;
		this.haltedMaxTimes=haltedMaxTimes;
		this.fitness=0;
		this.epsilon=epsilon;
		this.fitnessFunction = finFitnessFunction;
		this.comparator = comparator;
	}
	
	public boolean conditionMet(List<Fenotype> fenotypes) {

		Collections.sort(fenotypes, comparator);
		double actualfitness = fitnessFunction.evaluate(fenotypes.get(0));
		if(Math.abs(actualfitness - fitness) < epsilon) {
			this.haltedTimes++;
			if(haltedTimes >= haltedMaxTimes) {
				return false;
			}
		}
		else {
			this.fitness=actualfitness;
			this.haltedTimes=0;
		}
		
		return true;
	}

	
}
