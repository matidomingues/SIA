package ar.edu.itba.sia.genetics;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.impl.NeuralNetworkFenotype;
import ar.edu.itba.sia.genetics.operators.GeneticOperator;
import ar.edu.itba.sia.genetics.replacers.GeneticReplacer;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.ArrayList;
import java.util.List;

public class Genetics {

	private FenotypeSelector selector;
	private GeneticOperator operator;
	private GeneticReplacer replacer;
	private ReplacementAlgorithm replacementAlgorithm;
	private CutCondition cutCondition;
	private int[] arquitecture= {2,10,1}; 
	
	public static void main(String[] args) {

		Genetics genetics= new Genetics();
		List<Fenotype> fenotypes = genetics.initPopulation(100);
		ReplacementAlgorithm loop = genetics.getReplacementAlgorithm();

		while (genetics.cutConditionMet()) {
			loop.evolve(fenotypes);
		}
	}
	
	public Genetics(){
		
	}
	
	public Genetics(FenotypeSelector selector, GeneticOperator operator, ReplacementAlgorithm replacementAlgorithm,CutCondition cutCondition,int N){
		this.cutCondition=cutCondition;
	}

	private List<Fenotype> initPopulation(int N) {
		List<Fenotype> population= new ArrayList<Fenotype>(N);
		for(int i=0;i<N;i++){
			population.add(new NeuralNetworkFenotype(arquitecture));
		}
		return population; 
	}

	private boolean cutConditionMet() {
		return cutCondition.condition(null);
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
	
	public ReplacementAlgorithm getReplacementAlgorithm(){
		return replacementAlgorithm;
	}
}
