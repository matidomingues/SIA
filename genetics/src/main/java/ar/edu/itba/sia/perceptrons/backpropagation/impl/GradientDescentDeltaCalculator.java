package ar.edu.itba.sia.perceptrons.backpropagation.impl;

import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import ar.edu.itba.sia.perceptrons.backpropagation.DeltaCalculator;
import ar.edu.itba.sia.utils.MatrixFunction;
import org.jblas.DoubleMatrix;
import org.jblas.ranges.IntervalRange;

import java.util.List;

public class GradientDescentDeltaCalculator implements DeltaCalculator {

	private final double etha;
	private final MatrixFunction function;

	public GradientDescentDeltaCalculator(double etha, MatrixFunction function) {
		this.etha = etha;
		this.function = function;
	}

	
	public void calculate(PerceptronNetwork network, Pattern pattern,
						  List<DoubleMatrix> hs, List<DoubleMatrix> vs, List<DoubleMatrix> deltas) {
		for (int i = 0; i < network.getLayers().size(); i++) { deltas.add(null); }
		DoubleMatrix lastV = vs.get(vs.size() - 1);
		DoubleMatrix d = function.apply(hs.get(hs.size() - 1))
				.mul(pattern.getExpectedOutputs()
						.sub(lastV.get(new IntervalRange(0, lastV.rows), new IntervalRange(1, lastV.columns))));
		deltas.set(deltas.size() - 1, d);
		for (int i = deltas.size() - 1; i > 0; i--) {
			DoubleMatrix weight = network.getLayers().get(i).getWeights();
			d = function.apply(hs.get(i - 1)).mul(deltas.get(i)
					.mmul(weight.get(new IntervalRange(0, weight.rows), new IntervalRange(1, weight.columns))));
			deltas.set(i-1, d);
		}

		for (int i = 0; i< deltas.size(); i++) {
			DoubleMatrix newDelta = deltas.get(i).transpose().mmul(vs.get(i)).mul(etha);
			deltas.set(i, newDelta);
		}
	}
}
