package ar.edu.itba.sia.genetics.replacers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.backpropagation.Backpropagator;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.*;

public class ReplacementAlgorithmTwo extends ReplacementAlgorithm {


	public ReplacementAlgorithmTwo(FenotypeSelector selectorSelect, FenotypeSelector selectorReplace, Mutator mutator, Crossover crosser, Backpropagator backpropagator) {
		super(selectorSelect, selectorReplace, mutator, crosser, backpropagator);
	}

	@Override
	public void evolve(List<Fenotype> fenotypes) {
		List<Fenotype> children = new ArrayList<Fenotype>();
		List<Fenotype> parents = this.getSelectorSelect().select(fenotypes);
		
		while(children.size() < parents.size()) {
			Collections.shuffle(parents);
			children.addAll(this.getCrosser().crossover(parents.get(0), parents.get(1)));
		}
		List<Fenotype> mutations = new ArrayList<Fenotype>();
		for (Fenotype f : children) {
			f=this.getMutator().mutate(f);
			mutations.add(this.getBackpropagator().backpropagate(f));		
		}
		List<Fenotype> oldGenerationRemains=this.getSelectorReplace().select(fenotypes);
		fenotypes.retainAll(oldGenerationRemains);
		fenotypes.addAll(mutations);
	}
}
