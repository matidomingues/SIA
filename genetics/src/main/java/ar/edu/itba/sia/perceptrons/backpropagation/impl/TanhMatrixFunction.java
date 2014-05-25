package ar.edu.itba.sia.perceptrons.backpropagation.impl;

import ar.edu.itba.sia.utils.MatrixFunction;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

public class TanhMatrixFunction implements MatrixFunction{

	private final double beta;

	public TanhMatrixFunction() {
		this(1.0);
	}

	public TanhMatrixFunction(double beta) {
		this.beta = beta;
	}

	
	public DoubleMatrix apply(DoubleMatrix matrix) {
		return MatrixFunctions.tanh(matrix.mul(beta)); // tanh(beta * matrix);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) return false;

		if (obj == this) return true;

		TanhMatrixFunction f = (TanhMatrixFunction)obj;
		if (f.beta != this.beta) return false;

		return true;
	}
}
