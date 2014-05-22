package ar.edu.itba.sia.genetics.cutcondition.impl;

import java.util.Collections;
import java.util.List;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;

public class ScopeCutCondition implements CutCondition{

	private double maxScope;
	public ScopeCutCondition(double maxScope){
		this.maxScope=maxScope;
	}
	
	public boolean conditionMet(Object o) {
		if(!(o instanceof List<?>)){
			throw new IllegalArgumentException();
		}
		
		List<Fenotype> fenotypes=(List<Fenotype>)o;
		Collections.sort(fenotypes, new FenotypeComparator());
		double actualfitness=fenotypes.get(0).fitnessFunction();
		if(actualfitness>maxScope){
			return false;
		}		
		return true;
	}

}
