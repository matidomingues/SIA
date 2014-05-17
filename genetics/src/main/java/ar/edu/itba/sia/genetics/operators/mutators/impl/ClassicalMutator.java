package ar.edu.itba.sia.genetics.operators.mutators.impl;

import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;

public class ClassicalMutator extends Mutator{

	@Override
	public Fenotype mutate(Fenotype fenotype) {
		if(super.domutation()){	
			Random random = new Random(System.nanoTime());
			double max=fenotype.maxRange();
			double min=fenotype.minRange();
			int locus=random.nextInt(fenotype.size()); //PREGUNTAR: se modifica un alelo o un gen completo?
			double weight=random.nextDouble()*(max-min)+min; //PREGUNTAR: la probabilidad es uniforme o puede ser gaussiana?
			fenotype.alter(locus, weight);
		}
		return fenotype;
			
	}

}
