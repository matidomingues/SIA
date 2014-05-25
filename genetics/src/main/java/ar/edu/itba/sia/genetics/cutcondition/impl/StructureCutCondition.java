package ar.edu.itba.sia.genetics.cutcondition.impl;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

public class StructureCutCondition implements CutCondition{

	private final double populationPercentage;
	private final int maxConsecutiveTimes;
	private List<Fenotype> lastGeneration;
	private int repeatedTimes=0;

	public StructureCutCondition(double populationPercentage, int maxConsecutiveTimes) {
		this.populationPercentage = populationPercentage;
		this.maxConsecutiveTimes = maxConsecutiveTimes;
		this.lastGeneration=new ArrayList<Fenotype>();
	}
	
	
	public boolean conditionMet(List<Fenotype> fenotypes) {
		if(lastGeneration.isEmpty()){
			return true;
		}
		int repeated=0;
		for(Fenotype f: fenotypes){
			if(lastGeneration.contains(f)){
				repeated++;
			}
		}
		
		if((repeated/lastGeneration.size())> populationPercentage){
			if(repeatedTimes>= maxConsecutiveTimes){
				return false;
			}
			else{
				repeatedTimes++;
			}
		}
				
		return true;
	}

}
