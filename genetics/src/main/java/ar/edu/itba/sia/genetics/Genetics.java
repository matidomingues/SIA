package ar.edu.itba.sia.genetics;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.fenotypes.impl.NeuralNetworkFenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.impl.PerceptronNetworkFitnessFunction;
import ar.edu.itba.sia.genetics.operators.GeneticOperator;
import ar.edu.itba.sia.genetics.replacers.GeneticReplacer;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;
import ar.edu.itba.sia.perceptrons.Layer;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.GradientDescentDeltaCalculator;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.TanhDMatrixFunction;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.TanhMatrixFunction;
import ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition;
import ar.edu.itba.sia.perceptrons.utils.EpochsCutCondition;
import ar.edu.itba.sia.perceptrons.utils.ErrorCutCondition;
import ar.edu.itba.sia.services.ConfigurationService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jblas.DoubleMatrix;

public class Genetics {

	private final ReplacementAlgorithm replacementAlgorithm;
	private final CutCondition cutCondition;
	private final List<Fenotype> fenotypes;
	private final FenotypeBuilder fenotypeBuilder;

	public static void main(String[] args) {

		ReplacementAlgorithm replacementAlgorithm = ConfigurationService.getInstance().getReplacementAlgorithm();
		CutCondition cutCondition = ConfigurationService.getInstance().getGeneticCutCondition();
		FenotypeBuilder fenotypeBuilder = ConfigurationService.getInstance().getFenotypeBuilder();
		int population = ConfigurationService.getInstance().getPopulation();

		Genetics genetics= new Genetics(replacementAlgorithm, cutCondition, fenotypeBuilder, population);

		while (genetics.cutConditionMet()) {
			genetics.work();
		}
	}

	public Genetics(ReplacementAlgorithm replacementAlgorithm, CutCondition cutCondition, FenotypeBuilder fenotypeBuilder, int population){
		this.replacementAlgorithm = replacementAlgorithm;
		this.cutCondition = cutCondition;
		this.fenotypeBuilder = fenotypeBuilder;
		this.fenotypes = initPopulation(population);
	}

	public void work() {
		replacementAlgorithm.equals(fenotypes);
	}

	private List<Fenotype> initPopulation(int N) {
		List<Fenotype> population= new ArrayList<Fenotype>(N);
		for(int i=0;i<N;i++){
			population.add(fenotypeBuilder.build());
		}
		for (Fenotype f : population) {
			this.replacementAlgorithm.getBackpropagator().forceBackpropagation(f);
		}
		return population; 
	}
	
	private boolean cutConditionMet() {
		return cutCondition.conditionMet(fenotypes);
	}
}
