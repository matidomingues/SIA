package ar.edu.itba.sia.genetics.cutcondition.impl;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.List;

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
	
	public boolean conditionMet(List<Fenotype> fenotypes) {
		incGeneration();		
		return cantGenerations<=maxGenerations;
	}

}
