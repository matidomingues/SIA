package ar.edu.itba.sia.genetics.operators.mutators.impl;

import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;

public class ModernMutator extends Mutator {

	@Override
	public Fenotype mutate(Fenotype fenotype) {
		if (super.domutation()) {
			Random random = new Random(System.nanoTime());
			double max = fenotype.maxRange();
			double min = fenotype.minRange();
			int locus1, locus2,minLocus,maxLocus;
			do {
				locus1 = random.nextInt(fenotype.size());
				locus2 = random.nextInt(fenotype.size());
			} while (locus1 == locus2);

			minLocus=Math.min(locus1, locus2);
			maxLocus=Math.max(locus1, locus2);
			
			for(int i=minLocus;i<=maxLocus;i++){    //PREGUNTAR: verificar si esta bien la mutacion
				double weight = random.nextDouble() * (max - min) + min; // PREGUNTAR: la probabilidad es uniforme o puede ser gaussiana?
				fenotype.alter(i, weight);	
			}
		}
		return fenotype;
	}
}
