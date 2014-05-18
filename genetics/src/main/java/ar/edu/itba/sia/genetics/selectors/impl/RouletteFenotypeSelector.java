package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class RouletteFenotypeSelector implements FenotypeSelector{

	private int k;
	
	public RouletteFenotypeSelector(int k){
		this.k=k;
	}
	
	public Set<Fenotype> select(Set<Fenotype> fenotypes) {
		List<Fenotype> array=new ArrayList<Fenotype>(fenotypes);
		
		return null;
	}

}
