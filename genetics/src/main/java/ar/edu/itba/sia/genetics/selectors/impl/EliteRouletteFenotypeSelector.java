package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.Set;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class EliteRouletteFenotypeSelector implements FenotypeSelector{
	private EliteFenotypeSelector eliteSelector;
	private RouletteFenotypeSelector rouletteSelector;
	
	
	public EliteRouletteFenotypeSelector(int G, int a){
		this.eliteSelector=new EliteFenotypeSelector(G/a);
		this.rouletteSelector=new RouletteFenotypeSelector((G*(a-1))/a);
	}
	
	public Set<Fenotype> select(Set<Fenotype> fenotypes) {
		Set<Fenotype> set= eliteSelector.select(fenotypes);
		set.addAll(rouletteSelector.select(fenotypes));
		return set;
	}
}
