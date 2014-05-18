package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;

public class TournamentsDeterministicFenotypeSelector implements
		FenotypeSelector {

	private int k;
	private int m;

	public TournamentsDeterministicFenotypeSelector(int m, int k) {
		this.k = k;
		this.m = m;
	}

	public Set<Fenotype> select(Set<Fenotype> fenotypes) {
		
		List<Fenotype> array=new ArrayList<Fenotype>(fenotypes);
		Collections.sort(array, new FenotypeComparator());
		Set<Fenotype> selectedSet=new HashSet<Fenotype>();
		
		int size=array.size();
		
		for(int i=0;i<k;i++){
			
			Random random = new Random(System.nanoTime());
			int elite=0;
			int[] individuals= new int[m];
			for(int j=0;j<m;j++){
				individuals[j]=random.nextInt(size);
				elite=(elite>individuals[j])?individuals[j]:elite;
			}
			selectedSet.add(array.get(elite));
		}
		
		return selectedSet;
	}

}
