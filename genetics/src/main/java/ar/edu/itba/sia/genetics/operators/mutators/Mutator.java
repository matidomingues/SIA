package ar.edu.itba.sia.genetics.operators.mutators;

import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

public abstract class Mutator {
	private static final double MUTATOR_PROBABILITY=0.5;
	public abstract Fenotype mutate(Fenotype fenotype);
	
	
	public boolean domutation(){
		Random random = new Random(System.nanoTime());
		return random.nextDouble()>=MUTATOR_PROBABILITY;
	}
}
