package ar.edu.itba.sia.perceptrons.backpropagation;

import java.util.*;

import ar.edu.itba.sia.perceptrons.Layer;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
public class BackpropagationAlgorithm {

	private final DeltaCalculator deltaCalculator;
	private List<Pattern> patterns;
	private PerceptronNetwork network;
	private int epochs = 5000;
	private final List<Double> errorHistory;
	private double epsilon = 0.0001;
	private final CutCondition cutCondition;

	public BackpropagationAlgorithm(DeltaCalculator deltaCalculator, CutCondition cutCondition) {
		this.deltaCalculator = deltaCalculator;
		this.errorHistory = new LinkedList<Double>();
		this.cutCondition = cutCondition;
	}

	public void execute(PerceptronNetwork network, List<Pattern> patterns) {
		this.patterns = patterns;
		this.network = network;
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
		} while (!cutCondition.conditionMet(this));
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

	public int getEpochs() {
		return epochs;
	}

	public double getLastError() {
		return errorHistory.get(errorHistory.size() - 1);
	}

	public List<Double> getErrorHistory() {
		return Collections.unmodifiableList(errorHistory);
	}

	public PerceptronNetwork getNetwork() {
		return this.network;
	}

	public List<Pattern> getPatterns() {
		return this.patterns;
	}
}
