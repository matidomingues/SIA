package ar.edu.itba.sia.genetics.operators;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.Set;

public interface GeneticOperator {
	void evolve(Set<Fenotype> selected);
}
