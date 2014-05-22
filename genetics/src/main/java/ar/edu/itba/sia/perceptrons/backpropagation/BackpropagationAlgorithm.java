package ar.edu.itba.sia.perceptrons.backpropagation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.perceptrons.Layer;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import org.jblas.ranges.IntervalRange;
import org.jblas.ranges.PointRange;


public class BackpropagationAlgorithm {

	private final DeltaCalculator deltaCalculator;
	private int epochs = 5000;
	private final List<Double> errorHistory;
	private double epsilon = 0.0001;

	public BackpropagationAlgorithm(DeltaCalculator deltaCalculator) {
		this.deltaCalculator = deltaCalculator;
		this.errorHistory = new LinkedList<Double>();
	}

	public void execute(PerceptronNetwork network, List<Pattern> patterns) {
		List<DoubleMatrix> vs = new ArrayList<DoubleMatrix>(network.getLayers().size() + 1);
		List<DoubleMatrix> hs = new ArrayList<DoubleMatrix>(network.getLayers().size());
		List<DoubleMatrix> deltas = new ArrayList<DoubleMatrix>(network.getLayers().size());
		do {
			Collections.shuffle(patterns);
			for(Pattern p : patterns) {
				computeOutputs(network.getLayers(), p, vs, hs);
				deltaCalculator.calculate(network, p, hs, vs, deltas);
				network.updateLayers(deltas);
				vs.clear(); hs.clear(); deltas.clear();
			}
		} while (cutCondition(network, patterns) == false);
	}

	private void computeOutputs(List<Layer> layers, Pattern pattern, List<DoubleMatrix> vs, List<DoubleMatrix> hs) {
		DoubleMatrix previousOuptut = pattern.getExtendedInputValues();
		vs.add(pattern.getExtendedInputValues());

		for(Layer l: layers) {
			DoubleMatrix h = l.getH(previousOuptut);
			hs.add(h);
			DoubleMatrix v = l.getTransferenceFunction().apply(h);
			v = Pattern.extendMatrix(v);
			vs.add(v);
			previousOuptut = v;
		}
	}

	private boolean cutCondition(PerceptronNetwork network, List<Pattern> patterns) {
		epochs--;
		double error = 0.0;
		for (Pattern p : patterns) {
			DoubleMatrix result = network.compute(p);
			error += MatrixFunctions.pow(p.getExpectedOutputs().sub(result),2).scalar();
		}
		errorHistory.add(error);
		return
				// epochs <= 0 ||
						error <= epsilon;  //To change body of created methods use File | Settings | File Templates.
	}

	public int getEpochs() {
		return epochs;
	}

	public double getLastError() {
		return errorHistory.get(errorHistory.size() - 1);
	}

	public List<Double> getErrorHistory() {
		return Collections.unmodifiableList(errorHistory);
	}
}
