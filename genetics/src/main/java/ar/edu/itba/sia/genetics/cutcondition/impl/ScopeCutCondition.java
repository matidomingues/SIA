package ar.edu.itba.sia.genetics.cutcondition.impl;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScopeCutCondition implements CutCondition{

	private final FitnessFunction fitnessFunction;
	private final Comparator<Fenotype> comparator;
	private final double maxScope;

	public ScopeCutCondition(double maxScope, FitnessFunction fitnessFunction, Comparator<Fenotype> comparator){
		this.maxScope=maxScope;
		this.fitnessFunction = fitnessFunction;
		this.comparator = comparator;
	}
	
	public boolean conditionMet(List<Fenotype> fenotypes) {
		Collections.sort(fenotypes, comparator);
		double actualfitness = fitnessFunction.evaluate(fenotypes.get(0));
		if(actualfitness>maxScope){
			return false;
		}		
		return true;
	}

}
