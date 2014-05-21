package ar.edu.itba.sia.genetics.replacers;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.backpropagation.Backpropagator;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.List;

public abstract class ReplacementAlgorithm {

	private FenotypeSelector selectorSelect;
	private FenotypeSelector selectorReplace;
	private Mutator mutator;
	private Crossover crosser;
	private Backpropagator backpropagator;

	protected ReplacementAlgorithm(FenotypeSelector selectorSelect, FenotypeSelector selectorReplace, Mutator mutator, Crossover crosser, Backpropagator backpropagator) {
		this.selectorSelect = selectorSelect;
		this.selectorReplace=selectorReplace;
		this.mutator = mutator;
		this.crosser = crosser;
		this.backpropagator=backpropagator;
	}

	public abstract void evolve(List<Fenotype> fenotypes);

	public FenotypeSelector getSelectorSelect() {
		return this.selectorSelect;
	}
	
	public FenotypeSelector getSelectorReplace(){
		return this.selectorReplace;
	}

	public Mutator getMutator() {
		return this.mutator;
	}

	public Crossover getCrosser() {
		return this.crosser;
	}
	
	public Backpropagator getBackpropagator(){
		return this.backpropagator;
	}
}
