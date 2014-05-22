package ar.edu.itba.sia.perceptrons;

import org.jblas.DoubleMatrix;
import org.jblas.ranges.IntervalRange;
import org.jblas.ranges.PointRange;

public class Pattern {
	private static final int OUTPUT_QTY = 1;
	private final int OUTPUT_POS;
	private final DoubleMatrix pattern;

	public Pattern(DoubleMatrix pattern) {
		if (!pattern.isRowVector() || pattern.isScalar() || pattern.isColumnVector())
			throw new IllegalArgumentException("Pattern must be a row vector.");
		if (pattern.isEmpty())
			throw new IllegalArgumentException("Pattern is empty!");
		this.pattern = pattern;
		this.OUTPUT_POS = pattern.columns - 1;
	}

	public DoubleMatrix getExpectedOutputs() {
		return pattern.get(new PointRange(0), new PointRange(OUTPUT_POS));
	}

	public DoubleMatrix getInputValues() {
		return pattern.get(new PointRange(0), new IntervalRange(0, pattern.columns - OUTPUT_QTY));
	}

	public DoubleMatrix getExtendedInputValues() {
		return extendMatrix(getInputValues());
	}

	public static DoubleMatrix extendMatrix(DoubleMatrix input) {
		DoubleMatrix extendedInput = new DoubleMatrix(input.columns + 1).transpose();
		extendedInput.put(0, -1.0).put(new PointRange(0), new IntervalRange(1, extendedInput.columns), input);
		return extendedInput;
	}
}
