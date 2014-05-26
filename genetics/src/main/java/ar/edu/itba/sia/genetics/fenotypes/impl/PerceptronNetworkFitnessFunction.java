package ar.edu.itba.sia.genetics.fenotypes.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

import java.util.Collections;
import java.util.List;

public class PerceptronNetworkFitnessFunction implements FitnessFunction {

	private final List<Pattern> patterns;

	public PerceptronNetworkFitnessFunction(List<Pattern> patterns) {
		this.patterns = Collections.unmodifiableList(patterns);
	}

	@Override
	public double evaluate(Fenotype f) {
		if (!(f instanceof PerceptronNetwork)) throw new IllegalArgumentException("Fenotype must be a Perceptron Network!");

		PerceptronNetwork network = (PerceptronNetwork)f;
		double error = 0.0;
		for (Pattern p : patterns) {
			DoubleMatrix result = network.compute(p);
			error += MatrixFunctions.pow(p.getExpectedOutputs().sub(result), 2).scalar();
		}
		//System.out.println((error/patterns.size()));
		
		return  1.0/(error/patterns.size());
	}
}
