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

public class TournamentsProbabilisticsFenotypeSelector implements FenotypeSelector{

	private int k;
	
	
	public TournamentsProbabilisticsFenotypeSelector(int k){
		this.k=k;
	}
	
	public Set<Fenotype> select(Set<Fenotype> fenotypes) {
		List<Fenotype> array=new ArrayList<Fenotype>(fenotypes);
		Collections.sort(array, new FenotypeComparator());
		Set<Fenotype> selectedSet=new HashSet<Fenotype>();
		
		int size=array.size();
		
		for(int i=0;i<k;i++){
			
			Random random = new Random(System.nanoTime());
			int elite=0;
			int[] individuals= new int[2];
			individuals[0]=random.nextInt(size);
			individuals[1]=random.nextInt(size);
			double probability= random.nextDouble();
			if(probability<0.75){
				elite=Math.max(individuals[0], individuals[1]);				
			}
			else{
				elite=Math.min(individuals[0], individuals[1]);
			}
			
			selectedSet.add(array.get(elite));
		}
		
		return selectedSet;
	}

}
