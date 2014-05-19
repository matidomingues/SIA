package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class TournamentsProbabilisticsFenotypeSelector implements FenotypeSelector{

	private int k;
	
	
	public TournamentsProbabilisticsFenotypeSelector(int k){
		this.k=k;
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		List<Fenotype> selectedList= new ArrayList<Fenotype>(k);
		int size=fenotypes.size();
		
		for(int i=0;i<k;i++){
			
			Random random = new Random(System.nanoTime());
			Fenotype elite;
			int[] individuals= new int[2];
			individuals[0]=random.nextInt(size);
			individuals[1]=random.nextInt(size);
			Fenotype f1,f2;
			f1=fenotypes.get(individuals[0]);
			f2=fenotypes.get(individuals[1]);
			double probability= random.nextDouble();
			if(probability<0.75){
				elite=(f1.fitnessFunction()>f2.fitnessFunction())?f1:f2;				
			}
			else{
				elite=(f1.fitnessFunction()<f2.fitnessFunction())?f1:f2;
			}
			
			selectedList.add(elite);
		}
		
		return selectedList;
	}

}
