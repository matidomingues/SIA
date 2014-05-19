package ar.edu.itba.sia.genetics.cutcondition.impl;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;

public class StructureCutCondition implements CutCondition{

	private double populationPercentage;
	private int consecutiveTimes;
	
	public StructureCutCondition(int populationPercentage){
		this.populationPercentage=populationPercentage;
	}
	
	
	public boolean condition(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

}
