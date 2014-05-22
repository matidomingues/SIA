package ar.edu.itba.sia.perceptrons;

import org.jblas.DoubleMatrix;
import org.jblas.ranges.IntervalRange;

import java.util.List;

public class PerceptronNetwork {

	private final List<Layer> layers;

	public PerceptronNetwork(List<Layer> layers) {
		this.layers = layers;
	}

	public List<Layer> getLayers() {
		return this.layers;
	}

	public DoubleMatrix compute(Pattern pattern) {
		DoubleMatrix output = pattern.getExtendedInputValues();
		for(Layer l : layers) {
			output = Pattern.extendMatrix(l.getOutput(output));
		}
		return output.get(new IntervalRange(0, output.rows), new IntervalRange(1, output.columns));
	}

	public void updateLayers(List<DoubleMatrix> deltas) {
		if (deltas.size() != layers.size()) throw new IllegalArgumentException("List sizes mistmatch.");
		for (int i = 0; i < deltas.size(); i++) {
			layers.get(i).getWeights().addi(deltas.get(i));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < layers.size(); i++) {
			sb.append(layers.get(i)).append('\n');
		}
		return sb.toString();
	}
}
