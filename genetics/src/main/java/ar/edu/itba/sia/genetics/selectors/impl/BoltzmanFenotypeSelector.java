package ar.edu.itba.sia.genetics.selectors.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;
import org.jblas.MatrixFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoltzmanFenotypeSelector implements FenotypeSelector {

	private static double MAX_PROBABILITY=0.5;
	private final FitnessFunction fitnessFunction;
	private final int k;
	private double T;
	private int n;
	public BoltzmanFenotypeSelector(int k, FitnessFunction fitnessFunction){
		this.k=k;
		this.fitnessFunction = fitnessFunction;
		this.T = 0.0;
		this.n=1;
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		double[] f = new double[fenotypes.size()];
		double fAverage = 0;
		int i = 0;

		for(Fenotype fenotype:fenotypes){
			f[i]=MatrixFunctions.exp(fitnessFunction.evaluate(fenotype)/T);
			fAverage+=f[i];
			i++;
		}

		List<Fenotype> list= new ArrayList<Fenotype>();
		fAverage=fAverage/fenotypes.size();
		i=0;

		while(i<k){
			Random random = new Random(System.nanoTime());
			int chosen=random.nextInt(fenotypes.size());
			if(f[chosen]>MAX_PROBABILITY){
				list.add(fenotypes.get(chosen));
				i++;
			}
		}

		incGeneration();
		return list;
	}
	
	private void incGeneration(){
		n++;
		T=1/n;
	}

}
