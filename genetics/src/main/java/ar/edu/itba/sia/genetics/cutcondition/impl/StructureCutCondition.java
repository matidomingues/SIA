package ar.edu.itba.sia.genetics.cutcondition.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;

public class StructureCutCondition implements CutCondition{

	private static final double POPULATIONPERCENTAGE =0.9;
	private static final int MAXCONSECUTIVETIMES=10;
	private List<Fenotype> lastGeneration;
	private int repeatedTimes=0;
	public StructureCutCondition(){
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
		
		if((repeated/lastGeneration.size())>POPULATIONPERCENTAGE){
			if(repeatedTimes>=MAXCONSECUTIVETIMES){
				return false;
			}
			else{
				repeatedTimes++;
			}
		}
				
		return true;
	}

}
