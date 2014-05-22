package ar.edu.itba.sia.genetics.replacers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.backpropagation.Backpropagator;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ReplacementAlgorithmThree extends ReplacementAlgorithm {

private FenotypeSelector selectorReplace2;
	public ReplacementAlgorithmThree(FenotypeSelector selectorSelect, FenotypeSelector selectorReplace, FenotypeSelector selectorReplace2,Mutator mutator, Crossover crosser, Backpropagator backpropagator) {
		super(selectorSelect,selectorReplace, mutator, crosser,backpropagator);
		this.selectorReplace2=selectorReplace2;
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
		List<Fenotype> oldGeneration=this.getSelectorReplace().select(fenotypes);
		List<Fenotype> mix=new ArrayList<Fenotype>();
		mix.addAll(fenotypes);
		mix.addAll(mutations);
		List<Fenotype> newGeneration=this.getSelectorReplace2().select(mix);
		fenotypes.retainAll(oldGeneration);
		fenotypes.addAll(newGeneration);

	}

	private void shuffle(Fenotype[] ar)
	{
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Fenotype a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
	public FenotypeSelector getSelectorReplace2() {
		return this.selectorReplace2;
	}
}
