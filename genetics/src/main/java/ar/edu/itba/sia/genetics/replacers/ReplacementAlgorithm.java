package ar.edu.itba.sia.genetics.replacers;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.crossers.CrossoverAlgorithm;
import ar.edu.itba.sia.genetics.operators.mutators.MutationAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.Set;

public abstract class ReplacementAlgorithm {

	private FenotypeSelector selector;
	private MutationAlgorithm mutator;
	private CrossoverAlgorithm crosser;

	protected ReplacementAlgorithm(FenotypeSelector selector, MutationAlgorithm mutator, CrossoverAlgorithm crosser) {
		this.selector = selector;
		this.mutator = mutator;
		this.crosser = crosser;
	}

	public abstract void evolve(Set<Fenotype> fenotypes);

	public FenotypeSelector getSelector() {
		return this.selector;
	}

	public MutationAlgorithm getMutator() {
		return this.mutator;
	}

	public CrossoverAlgorithm getCrosser() {
		return this.crosser;
	}
}
