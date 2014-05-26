package ar.edu.itba.sia.genetics;

import java.util.*;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition;
import ar.edu.itba.sia.perceptrons.utils.ErrorCutCondition;
import ar.edu.itba.sia.services.ConfigurationService;
import ar.edu.itba.sia.services.ExportService;

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

			ar.edu.itba.sia.perceptrons.backpropagation.CutCondition bpCutCondition =
					ConfigurationService.getInstance().getBackpropagationCutCondition();
			if (bpCutCondition instanceof ChainedCutCondition) {
				for (ar.edu.itba.sia.perceptrons.backpropagation.CutCondition cc : ((ChainedCutCondition) bpCutCondition).getCutConditions()) {
					if (cc instanceof ErrorCutCondition) {
						ExportService.getInstance().exportErrorImage(((ErrorCutCondition) cc).getErrorHistory());
					}
				}
			}
		}
	}

	public Genetics(ReplacementAlgorithm replacementAlgorithm, CutCondition cutCondition, FenotypeBuilder fenotypeBuilder,	int population) {
		this.replacementAlgorithm = replacementAlgorithm;
		this.cutCondition = cutCondition;
		this.fenotypeBuilder = fenotypeBuilder;
		this.fenotypes = initPopulation(population);
	}


	public void work() {
		replacementAlgorithm.evolve(fenotypes);
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
