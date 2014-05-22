package ar.edu.itba.sia.perceptrons.backpropagation;

import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import org.jblas.DoubleMatrix;

import java.util.List;

public interface DeltaCalculator {
	void calculate(PerceptronNetwork network, Pattern pattern, List<DoubleMatrix> hs, List<DoubleMatrix> vs, List<DoubleMatrix> deltas);
}
