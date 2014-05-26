package ar.edu.itba.sia.genetics.operators.mutators.impl;

import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;

public class ClassicMutator extends Mutator{

	public ClassicMutator(double probability) {
		super(probability);
	}

	@Override
	public Fenotype mutate(Fenotype fenotype) {
		if(super.domutation()){	
			Random random = new Random(System.nanoTime());
			int locus=random.nextInt(fenotype.size()); 
			fenotype.alter(locus);
		}
		return fenotype;
			
	}

}
