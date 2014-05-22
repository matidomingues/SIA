package ar.edu.itba.sia.perceptrons.backpropagation.impl;

import ar.edu.itba.sia.perceptrons.backpropagation.WeightUpdater;
import org.jblas.DoubleMatrix;

import java.util.List;

public class SimpleWeightUpdater implements WeightUpdater {

	private final double etha;

	public SimpleWeightUpdater(double etha) {
		this.etha = etha;
	}

	@Override
	public void update(List<DoubleMatrix> weights, List<DoubleMatrix> vs, List<DoubleMatrix> deltas) {
		for (int i = 0; i < weights.size(); i ++) {
			weights.get(i).addi(deltas.get(i).mmul(vs.get(i)).mul(etha));
		}
	}
}
