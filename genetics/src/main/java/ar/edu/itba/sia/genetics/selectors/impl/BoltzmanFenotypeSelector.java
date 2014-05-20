package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.List;

import org.jblas.MatrixFunctions;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class BoltzmanFenotypeSelector implements FenotypeSelector {

	private int n;
	private int k;
	private double T;
	public BoltzmanFenotypeSelector(int k){
		this.k=k;
		this.n=1;
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		double[] f=new double[fenotypes.size()];
		double fAverage=0;
		int i=0;
		for(Fenotype fenotype:fenotypes){
			f[i]=MatrixFunctions.exp(fenotype.fitnessFunction()/T);
			fAverage+=f[i];
			i++;
		}
		
		fAverage=fAverage/fenotypes.size();
		
		
		incGeneration();
		return null;
	}
	
	private void incGeneration(){
		n++;
		T=1/n;
	}

}
