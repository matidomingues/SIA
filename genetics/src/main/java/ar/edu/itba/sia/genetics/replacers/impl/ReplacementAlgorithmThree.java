package ar.edu.itba.sia.genetics.replacers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.backpropagation.Backpropagator;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;
import ar.edu.itba.sia.genetics.selectors.impl.ChainedFenotypeSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ReplacementAlgorithmThree extends ReplacementAlgorithm {

	public ReplacementAlgorithmThree(FenotypeSelector selectorSelect, FenotypeSelector selectorReplace, Mutator mutator, Crossover crosser, Backpropagator backpropagator) {
		super(selectorSelect, selectorReplace, mutator, crosser, backpropagator);
		if (!(selectorReplace instanceof ChainedFenotypeSelector)) throw new IllegalArgumentException("This replacement algorithm requires a chained replacement selector");
		if (((ChainedFenotypeSelector)selectorReplace).getSelectors().size() < 2) throw new IllegalArgumentException("Too few selectors for replacement");
	}

	@Override
	public void evolve(List<Fenotype> fenotypes) {
		List<Fenotype> children =new ArrayList<Fenotype>();
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
		List<Fenotype> oldGeneration=((ChainedFenotypeSelector)this.getSelectorReplace()).getSelectors().get(0).select(fenotypes);
		List<Fenotype> mix=new ArrayList<Fenotype>();
		mix.addAll(fenotypes);
		mix.addAll(mutations);
		List<Fenotype> newGeneration=((ChainedFenotypeSelector)this.getSelectorReplace()).getSelectors().get(1).select(mix);
		fenotypes.retainAll(oldGeneration);
		fenotypes.addAll(newGeneration);

	}
}
