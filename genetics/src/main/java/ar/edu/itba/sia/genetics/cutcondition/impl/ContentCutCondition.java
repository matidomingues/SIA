package ar.edu.itba.sia.genetics.cutcondition.impl;

import java.util.Collections;
import java.util.List;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;

public class ContentCutCondition implements CutCondition{

	private int haltedTimes;
	private int haltedMaxTimes;
	private double fitness;
	private double epsilon;
	
	public ContentCutCondition(int haltedMaxTimes, double epsilon){
		this.haltedTimes=0;
		this.haltedMaxTimes=haltedMaxTimes;
		this.fitness=0;
		this.epsilon=epsilon;
	}
	
	public boolean condition(Object o) {	
		if(!(o instanceof List<?>)){
			throw new IllegalArgumentException();
		}
		
		List<Fenotype> fenotypes=(List<Fenotype>)o;
		Collections.sort(fenotypes, new FenotypeComparator());
		double actualfitness=fenotypes.get(0).fitnessFunction();
		if(Math.abs(actualfitness-fitness)<epsilon){
			this.haltedTimes++;
			if(haltedTimes>=haltedMaxTimes){
				return false;
			}
		}
		else{
			this.fitness=actualfitness;
			this.haltedTimes=0;
		}
		
		return true;
	}

	
}
