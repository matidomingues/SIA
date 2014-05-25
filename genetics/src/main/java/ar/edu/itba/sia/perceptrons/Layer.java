package ar.edu.itba.sia.perceptrons;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

public class Layer {

	private final DoubleMatrix weights;
	private final MatrixFunction transferenceFunction;

	public Layer(DoubleMatrix weights, MatrixFunction transferenceFunction) {
		this.weights = weights;
		this.transferenceFunction = transferenceFunction;
	}

	public DoubleMatrix getWeights() {
		return this.weights;
	}

	public MatrixFunction getTransferenceFunction() {
		return this.transferenceFunction;
	}

	public DoubleMatrix getOutput(DoubleMatrix pattern) {
		return transferenceFunction.apply(getH(pattern));
	}

	public DoubleMatrix getH(DoubleMatrix input) {
		return weights.mmul(input.transpose()).transpose();
	}

	@Override
	public String toString() {
		return weights.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) return false;

		if (obj == this) return true;

		Layer l = (Layer) obj;

		if (!l.getWeights().equals(this.getWeights())) return false;
		if (!l.getTransferenceFunction().equals(this.getTransferenceFunction())) return false;

		return true;
	}
}
