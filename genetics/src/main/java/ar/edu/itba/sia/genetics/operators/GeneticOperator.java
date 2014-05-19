package ar.edu.itba.sia.genetics.operators;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.List;

public interface GeneticOperator {
	void evolve(List<Fenotype> selected);
}
