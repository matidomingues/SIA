package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;

public class EliteFenotypeSelector implements FenotypeSelector{

	private final FitnessFunction fitnessFunction;
	private final int k;
	
	public EliteFenotypeSelector(FitnessFunction fitnessFunction, int k){
		this.fitnessFunction = fitnessFunction;
		this.k=k;
	}
	
	
	public void replace(Set<Fenotype> fenotypes, Set<Fenotype> selected) {
	}


	public List<Fenotype> select(List<Fenotype> fenotypes) {
		Collections.sort(fenotypes, new FenotypeComparator(fitnessFunction));
		List<Fenotype> selectedList= fenotypes.subList(0, k);
		
		return selectedList;
	}

}
