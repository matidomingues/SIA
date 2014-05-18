package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.replacers.GeneticReplacer;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class EliteFenotypeSelector implements FenotypeSelector{

	private int k;
	
	public EliteFenotypeSelector(int k){
		this.k=k;
	}
	
	
	public void replace(Set<Fenotype> fenotypes, Set<Fenotype> selected) {
	}


	public Set<Fenotype> select(Set<Fenotype> fenotypes) {
		TreeSet<Fenotype> set=new TreeSet<Fenotype>(new Comparator<Fenotype>() {
			
			public int compare(Fenotype o1, Fenotype o2) {
				return (int)(o1.fitnessFunction()-o2.fitnessFunction());
			}
		}) ;
		
		
		HashSet<Fenotype> answerSet=new HashSet<Fenotype>();
		Iterator<Fenotype> it = set.iterator();
		
		int i = 0;
		while(it.hasNext() && i < k) {
		   answerSet.add(it.next());
		   i++;
		}
		
		return answerSet;
	}

}
