package ar.edu.itba.sia.genetics.operators.mutators;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

public abstract class Mutator {

	public abstract Fenotype mutate(Fenotype fenotype);
}
