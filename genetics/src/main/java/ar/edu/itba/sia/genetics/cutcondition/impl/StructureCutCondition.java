package ar.edu.itba.sia.genetics.cutcondition.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;

public class StructureCutCondition implements CutCondition{

	private double populationPercentage;
	private int consecutiveTimes;
	private List<Fenotype> lastGeneration;
	public StructureCutCondition(int populationPercentage, int consecutiveTimes){
		this.populationPercentage=populationPercentage;
		this.consecutiveTimes=consecutiveTimes;
		this.lastGeneration=new ArrayList<Fenotype>();
	}
	
	
	public boolean condition(Object o) {
		if(!(o instanceof List<?>)){
			throw new IllegalArgumentException();
		}
		
		List<Fenotype> fenotypes=(List<Fenotype>)o;
		
		
		double actualfitness=fenotypes.get(0).fitnessFunction();
		if(actualfitness>maxScope){
			return false;
		}		
		return true;

	}

}
