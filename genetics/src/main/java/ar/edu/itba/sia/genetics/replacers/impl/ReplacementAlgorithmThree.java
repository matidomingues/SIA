package ar.edu.itba.sia.genetics.replacers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.crossers.CrossoverAlgorithm;
import ar.edu.itba.sia.genetics.operators.mutators.MutationAlgorithm;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ReplacementAlgorithmThree extends ReplacementAlgorithm {


	public ReplacementAlgorithmThree(FenotypeSelector selector, MutationAlgorithm mutator, CrossoverAlgorithm crosser) {
		super(selector, mutator, crosser);
	}

	@Override
	public void evolve(Set<Fenotype> fenotypes) {
		Set<Fenotype> children =new HashSet<Fenotype>();
		Fenotype[] parents = (Fenotype[])this.getSelector().select(fenotypes).toArray();
		while(children.size() < parents.length) {
			this.shuffle(parents);
			children.add(this.getCrosser().crossover(parents[0], parents[1]));
		}
		Set<Fenotype> mutations = new HashSet<Fenotype>();
		for (Fenotype f : children) {
			mutations.add(this.getMutator().mutate(f));
		}
		fenotypes.addAll(mutations);
		fenotypes.removeAll(this.getSelector().select(fenotypes));
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
}
