package ar.edu.itba.sia.genetics.operators.mutators;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

public interface Mutator {

	public Fenotype mutate(Fenotype fenotype);
}
