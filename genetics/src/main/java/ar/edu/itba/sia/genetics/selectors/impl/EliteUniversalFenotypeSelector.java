package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.List;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class EliteUniversalFenotypeSelector implements FenotypeSelector{
	
	private EliteFenotypeSelector eliteSelector;
	private UniversalFenotypeSelector universalSelector;
	
	
	public EliteUniversalFenotypeSelector(int G, int a){
		this.eliteSelector=new EliteFenotypeSelector(G/a);
		this.universalSelector=new UniversalFenotypeSelector((G*(a-1))/a);
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		List<Fenotype> list= eliteSelector.select(fenotypes);
		list.addAll(universalSelector.select(fenotypes));
		return list;
	}

}
