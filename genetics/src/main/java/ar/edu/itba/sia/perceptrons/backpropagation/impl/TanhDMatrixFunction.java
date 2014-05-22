package ar.edu.itba.sia.perceptrons.backpropagation.impl;

import ar.edu.itba.sia.utils.MatrixFunction;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

public class TanhDMatrixFunction implements MatrixFunction {

	private final double beta;

	public TanhDMatrixFunction() {
		this(1.0);
	}

	public TanhDMatrixFunction(double beta) {
		this.beta = beta;
	}
	@Override
	public DoubleMatrix apply(DoubleMatrix matrix) {
		return MatrixFunctions.pow(MatrixFunctions.tanh(matrix.mul(beta)), 2).rsub(1.0).mul(beta); // beta * (1 - tanh^2(beta * matrix))
	}
}
