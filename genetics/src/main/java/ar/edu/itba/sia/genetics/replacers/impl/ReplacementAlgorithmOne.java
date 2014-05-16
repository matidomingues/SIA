package ar.edu.itba.sia.genetics.replacers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.crossers.CrossoverAlgorithm;
import ar.edu.itba.sia.genetics.operators.mutators.MutationAlgorithm;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.HashSet;
import java.util.Set;

public class ReplacementAlgorithmOne extends ReplacementAlgorithm {


	public ReplacementAlgorithmOne(FenotypeSelector selector, MutationAlgorithm mutator, CrossoverAlgorithm crosser) {
		super(selector, mutator, crosser);
	}

	@Override
	public void evolve(Set<Fenotype> oldGeneration) {
		Set<Fenotype> newGeneration = new HashSet<Fenotype>();

		while (newGeneration.size() < oldGeneration.size()) {
			Set<Fenotype> selection = this.getSelector().select(oldGeneration);
			if (selection.size() != 2) {
				throw new IllegalStateException("Incompatible amount of parents during selection");
			}
			Fenotype[] parents = (Fenotype[])selection.toArray();
			Fenotype child = this.getCrosser().crossover(parents[0], parents[1]);
			child = this.getMutator().mutate(child);
			newGeneration.add(child);
		}

		oldGeneration.clear();
		oldGeneration.addAll(newGeneration);
	}
}
