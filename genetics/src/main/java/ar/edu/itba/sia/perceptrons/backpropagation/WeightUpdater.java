package ar.edu.itba.sia.perceptrons.backpropagation;

import org.jblas.DoubleMatrix;

import java.util.List;

public interface WeightUpdater {
	void update(List<DoubleMatrix> weights, List<DoubleMatrix> vs, List<DoubleMatrix> deltas);
}
