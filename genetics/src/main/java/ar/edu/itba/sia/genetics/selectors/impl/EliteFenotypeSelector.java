package ar.edu.itba.sia.genetics.selectors.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EliteFenotypeSelector implements FenotypeSelector{

	private final FitnessFunction fitnessFunction;
	private final Comparator<Fenotype> comparator;
	private final int k;
	
	public EliteFenotypeSelector(int k, FitnessFunction fitnessFunction, Comparator<Fenotype> comparator){
		this.fitnessFunction = fitnessFunction;
		this.k=k;
		this.comparator = comparator;
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		Collections.sort(fenotypes, comparator);
		List<Fenotype> selectedList= new ArrayList<Fenotype>(k);
		for (int i = 0; i < k; i++) {
			selectedList.add(fenotypes.get(i));
		}
		return selectedList;
	}

}
