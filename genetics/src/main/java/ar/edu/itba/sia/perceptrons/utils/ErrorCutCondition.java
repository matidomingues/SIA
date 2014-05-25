package ar.edu.itba.sia.perceptrons.utils;

import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;
import ar.edu.itba.sia.perceptrons.backpropagation.CutCondition;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

import java.util.LinkedList;
import java.util.List;

public class ErrorCutCondition implements CutCondition {

	private final List<Pattern> testPatterns;
	private final double epsilon;
	private final List<Double> errorHistory;

	public ErrorCutCondition(List<Pattern> testPatterns, double epsilon) {
		this.testPatterns = testPatterns;
		this.epsilon = epsilon;
		this.errorHistory = new LinkedList<Double>();
	}

	@Override
	public boolean conditionMet(BackpropagationAlgorithm backpropagationAlgorithm) {
		double error = 0.0;
		for (Pattern p : testPatterns) {
			DoubleMatrix result = backpropagationAlgorithm.getNetwork().compute(p);
			error += MatrixFunctions.pow(p.getExpectedOutputs().sub(result), 2).scalar();
		}
		errorHistory.add(error/2.0);
		return errorHistory.get(errorHistory.size() -1) > epsilon;
	}

	public List<Double> getErrorHistory() {
		return errorHistory;
	}
}
