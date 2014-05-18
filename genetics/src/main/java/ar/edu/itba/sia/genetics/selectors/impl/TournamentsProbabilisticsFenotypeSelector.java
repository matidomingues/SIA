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
			Fenotype elite;
			int[] individuals= new int[2];
			individuals[0]=random.nextInt(size);
			individuals[1]=random.nextInt(size);
			Fenotype f1,f2;
			f1=array.get(individuals[0]);
			f2=array.get(individuals[1]);
			double probability= random.nextDouble();
			if(probability<0.75){
				elite=(f1.fitnessFunction()>f2.fitnessFunction())?f1:f2;				
			}
			else{
				elite=(f1.fitnessFunction()<f2.fitnessFunction())?f1:f2;
			}
			
			selectedSet.add(elite);
		}
		
		return selectedSet;
	}

}
