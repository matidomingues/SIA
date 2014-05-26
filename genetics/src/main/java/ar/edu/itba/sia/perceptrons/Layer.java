package ar.edu.itba.sia.perceptrons;

import ar.edu.itba.sia.utils.MatrixFunction;
import org.apache.commons.lang.builder.HashCodeBuilder;
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

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();

		for (int i = 0; i < weights.rows; i++) {
			for (int j = 0; j < weights.columns; j++) {
				hcb = hcb.append(weights.get(i,j));
			}
		}

		return hcb.hashCode();
	}
}
