package ar.edu.itba.sia.genetics.cutcondition.impl;

import java.util.Collections;
import java.util.List;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;

public class ScopeCutCondition implements CutCondition{

	private final FitnessFunction fitnessFunction;
	private final double maxScope;

	public ScopeCutCondition(double maxScope, FitnessFunction fitnessFunction){
		this.maxScope=maxScope;
		this.fitnessFunction = fitnessFunction;
	}
	
	public boolean conditionMet(List<Fenotype> fenotypes) {
		Collections.sort(fenotypes, new FenotypeComparator());
		double actualfitness = fitnessFunction.evaluate(fenotypes.get(0));
		if(actualfitness>maxScope){
			return false;
		}		
		return true;
	}

}
