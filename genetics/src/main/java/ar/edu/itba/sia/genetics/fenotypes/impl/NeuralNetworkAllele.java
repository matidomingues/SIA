package ar.edu.itba.sia.genetics.fenotypes.impl;

import ar.edu.itba.sia.genetics.fenotypes.Allele;

public class NeuralNetworkAllele implements Allele{

	private double value;
	
	public NeuralNetworkAllele(double value){
		this.value=value;
	}
	
	public double getValue(){
		return value;
	}
}
