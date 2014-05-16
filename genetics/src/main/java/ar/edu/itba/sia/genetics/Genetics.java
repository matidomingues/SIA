package ar.edu.itba.sia.genetics;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.operators.GeneticOperator;
import ar.edu.itba.sia.genetics.replacers.GeneticReplacer;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.Set;

public class Genetics {

	private FenotypeSelector selector;
	private GeneticOperator operator;
	private GeneticReplacer replacer;

	public static void main(String[] args) {

		Set<Fenotype> fenotypes = initPopluation();
		FenotypeSelector selector = getSelector();
		GeneticOperator geneticOperator = getOperator();
		GeneticReplacer geneticReplacer = getReplacer();
		ReplacementAlgorithm loop = getReplacementAlgorithm();

		while (cutConditionMet()) {
			loop.evolve(fenotypes);
		}
	}

	private static Set<Fenotype> initPopluation() {
		// TODO: take from a file
		return null;  //To change body of created methods use File | Settings | File Templates.
	}

	private static boolean cutConditionMet() {
		// TODO: implemented depending on the condition;
		return false;  //To change body of created methods use File | Settings | File Templates.
	}


	public FenotypeSelector getSelector() {
		return selector;
	}

	public GeneticOperator getOperator() {
		return operator;
	}

	public GeneticReplacer getReplacer() {
		return replacer;
	}
}
