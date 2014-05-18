package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.Set;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class EliteUniversalFenotypeSelector implements FenotypeSelector{
	
	private EliteFenotypeSelector eliteSelector;
	private UniversalFenotypeSelector universalSelector;
	
	
	public EliteUniversalFenotypeSelector(int G, int a){
		this.eliteSelector=new EliteFenotypeSelector(G/a);
		this.universalSelector=new UniversalFenotypeSelector((G*(a-1))/a);
	}
	
	public Set<Fenotype> select(Set<Fenotype> fenotypes) {
		Set<Fenotype> set= eliteSelector.select(fenotypes);
		set.addAll(universalSelector.select(fenotypes));
		return set;
	}

}
