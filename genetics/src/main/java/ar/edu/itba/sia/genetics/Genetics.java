package ar.edu.itba.sia.genetics;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition;
import ar.edu.itba.sia.perceptrons.utils.ErrorCutCondition;
import ar.edu.itba.sia.services.ConfigurationService;
import ar.edu.itba.sia.services.ExportService;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		List<Double> errorHistory = new LinkedList<Double>();
		List<Double> fitnessHistory = new LinkedList<Double>();
		while (genetics.cutConditionMet()) {
			genetics.work();
//			ar.edu.itba.sia.perceptrons.backpropagation.CutCondition bpCutCondition =
//					ConfigurationService.getInstance().getBackpropagationCutCondition();
//			if (bpCutCondition instanceof ChainedCutCondition) {
//				for (ar.edu.itba.sia.perceptrons.backpropagation.CutCondition cc : ((ChainedCutCondition) bpCutCondition).getCutConditions()) {
//					if (cc instanceof ErrorCutCondition) {
//						ExportService.getInstance().exportErrorCSV(((ErrorCutCondition) cc).getErrorHistory());
//					}
//				}
//			}
			errorHistory.add(genetics.getMeanError(ConfigurationService.getInstance().getPatterns()));
			fitnessHistory.add(genetics.getMeanFitness());
		}

		ExportService.getInstance().exportFitnessCSV(fitnessHistory);
		ExportService.getInstance().exportErrorCSV(errorHistory);
		System.out.printf("Emc = %g\n", genetics.getMeanError(ConfigurationService.getInstance().getPatterns()));
		for(Fenotype f : genetics.fenotypes) {
			ExportService.getInstance().exportAsCSV((PerceptronNetwork)f);
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

	private double getMeanError(List<Pattern> patterns) {
		double error = 0.0;
		for(Fenotype f : this.fenotypes) {
			for (Pattern p : ConfigurationService.getInstance().getPatterns()) {
				DoubleMatrix result = ((PerceptronNetwork) f).compute(p);
				error = error + MatrixFunctions.pow(result, 2).scalar()/2.0;
			}
		}
		double size = fenotypes.size();
		return error/size;
	}

	private double getMeanFitness() {
		double fitness = 0.0;
		for (Fenotype f : this.fenotypes) {
			fitness += ConfigurationService.getInstance().getFitnessFunction().evaluate(f);
		}
		double size = fenotypes.size();
		return  fitness/size;
	}
}
