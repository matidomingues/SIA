package ar.edu.itba.sia.genetics.replacers;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.Set;

public abstract class ReplacementAlgorithm {

	private FenotypeSelector selector;
	private Mutator mutator;
	private Crossover crosser;

	protected ReplacementAlgorithm(FenotypeSelector selector, Mutator mutator, Crossover crosser) {
		this.selector = selector;
		this.mutator = mutator;
		this.crosser = crosser;
	}

	public abstract void evolve(Set<Fenotype> fenotypes);

	public FenotypeSelector getSelector() {
		return this.selector;
	}

	public Mutator getMutator() {
		return this.mutator;
	}

	public Crossover getCrosser() {
		return this.crosser;
	}
}
