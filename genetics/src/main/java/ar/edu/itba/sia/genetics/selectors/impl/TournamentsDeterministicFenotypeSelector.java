package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class TournamentsDeterministicFenotypeSelector implements
		FenotypeSelector {

	private final FitnessFunction fitnessFunction;
	private final int k;
	private final int m;

	public TournamentsDeterministicFenotypeSelector(int m, int k, FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		this.k = k;
		this.m = m;
	}

	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		
		List<Fenotype> selectedList=new ArrayList<Fenotype>(k);
		int size=fenotypes.size();
		
		for(int i=0;i<k;i++){
			
			Random random = new Random(System.nanoTime());
			int[] individuals= new int[m];
			individuals[0]=random.nextInt(size);
			Fenotype elite=fenotypes.get(individuals[0]);
			for(int j=1;j<m;j++){
				individuals[j]=random.nextInt(size);
				Fenotype fen=fenotypes.get(individuals[j]);
				elite=(fitnessFunction.evaluate(elite) < fitnessFunction.evaluate(fen))?fen:elite;
			}
			selectedList.add(elite);
		}
		
		return selectedList;
	}

}
