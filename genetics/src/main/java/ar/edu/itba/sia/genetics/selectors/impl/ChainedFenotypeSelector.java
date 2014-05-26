package ar.edu.itba.sia.genetics.selectors.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.ArrayList;
import java.util.List;

public class ChainedFenotypeSelector implements FenotypeSelector{
	private final List<FenotypeSelector> selectors;
	
	public ChainedFenotypeSelector(List<FenotypeSelector> selectors){
		this.selectors = selectors;
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		List<Fenotype> list=new ArrayList<Fenotype>();
		for(FenotypeSelector f: selectors){
			list.addAll(f.select(fenotypes));
		}
		return list;
	}

	public List<FenotypeSelector> getSelectors() {
		return this.selectors;
	}
}
