package ar.edu.itba.sia.genetics.cutcondition.impl;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;

public class GenerationsCutCondition implements CutCondition{

	private int cantGenerations;
	private int maxGenerations;
	
	public GenerationsCutCondition(int maxGenerations){
		this.cantGenerations=0;
		this.maxGenerations=maxGenerations;
	}
	
	private void incGeneration(){
		this.cantGenerations++;
	}
	
	public boolean condition(Object o) {
		incGeneration();		
		return cantGenerations<=maxGenerations;
	}

}
