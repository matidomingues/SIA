package ar.edu.itba.sia.genetics.replacers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.ArrayList;
import java.util.List;

public class ReplacementAlgorithmOne extends ReplacementAlgorithm {


	public ReplacementAlgorithmOne(FenotypeSelector selector, Mutator mutator, Crossover crosser) {
		super(selector, mutator, crosser);
	}

	@Override
	public void evolve(List<Fenotype> oldGeneration) {
		List<Fenotype> newGeneration = new ArrayList<Fenotype>();
		
		while (newGeneration.size() < oldGeneration.size()) {
			List<Fenotype> selection = this.getSelector().select(oldGeneration);
			if (selection.size() != 2) {
				throw new IllegalStateException("Incompatible amount of parents during selection");
			}
			List<Fenotype> childs = this.getCrosser().crossover(selection.get(0), selection.get(1));
			for(Fenotype child:childs){
				child = this.getMutator().mutate(child);
				newGeneration.add(child);
				
			}
		}

		oldGeneration.clear();
		oldGeneration.addAll(newGeneration);
	}
}
