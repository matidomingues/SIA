package ar.edu.itba.sia.genetics.fenotypes.impl;

import java.util.List;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;

public class NeuralNetworkFenotypeBuilder implements FenotypeBuilder{

	private int[] arquitecture;
	private int sizeArquitecture=0;
	
	public NeuralNetworkFenotypeBuilder(int[] arquitecture){
		this.arquitecture=arquitecture;
		for(int i=0;i<(arquitecture.length-1);i++){
			sizeArquitecture+=((arquitecture[i]+1)*(arquitecture[i+1]));
		}
	}
	
	public Fenotype build(List<Allele> childAlleles) {
		if(childAlleles.size()<sizeArquitecture){
			throw new IllegalArgumentException();
		}
		else{
			childAlleles=childAlleles.subList(0, sizeArquitecture);
		}
		
		return new NeuralNetworkFenotype(this.arquitecture, childAlleles);
	}

}
