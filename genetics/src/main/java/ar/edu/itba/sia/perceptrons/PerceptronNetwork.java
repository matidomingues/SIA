package ar.edu.itba.sia.perceptrons;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.impl.NeuralNetworkAllele;
import org.jblas.DoubleMatrix;
import org.jblas.ranges.IntervalRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerceptronNetwork implements Fenotype {

	private static final double MAX_RANGE = 30.5;
	private static final double MIN_RANGE = -30.5;

	private final List<Layer> layers;
	private final int locusCount;

	public PerceptronNetwork(List<Layer> layers) {
		this.layers = layers;
		int locusCount = 0;
		for (Layer l : layers) {
			locusCount += l.getWeights().getLength();
		}
		this.locusCount = locusCount;
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

	@Override
	public int size() {
		return locusCount;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public double maxRange() {
		return MAX_RANGE;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public double minRange() {
		return MIN_RANGE;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void alter(int locus) {
		if (locus < 0 || locus > locusCount ) throw new IndexOutOfBoundsException("No such locus");

		for(Layer l : layers) {
			int allelesLength= l.getWeights().getLength();
			if (locus > allelesLength) {
				locus -= allelesLength;
			} else if(locus >= 0) {
				DoubleMatrix dm = l.getWeights();
				Random random = new Random(System.nanoTime());
				int locusRow=locus/dm.columns;
				int locusColumn=locus-locusRow*dm.columns;
				double originalweight=dm.get(locusRow, locusColumn);
				double auxiweight=random.nextDouble()-0.5;
				dm.put(locusRow,locusColumn,originalweight+auxiweight);
				break;
			}
		}
	}

	@Override
	public List<Allele> getAlleles() {
		List<Allele> alleles = new ArrayList<Allele>(locusCount);
		for (Layer l : layers) {
			DoubleMatrix weights = l.getWeights();
			for (int i = 0; i < weights.rows; i++) {
				for (int j = 0; j < weights.columns; j++) {
					alleles.add(new NeuralNetworkAllele(weights.get(i, j)));
				}
			}
		}
		return alleles;  //To change body of implemented methods use File | Settings | File Templates.
 	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) return  false;

		if (obj == this) return true;

		PerceptronNetwork pn = (PerceptronNetwork) obj;

		if (pn.size() != this.size()) return false;
		if (pn.getLayers().size() != this.getLayers().size()) return false;

		for (int i = 0; i < this.size(); i++) {
			if (!pn.getLayers().get(i).equals(this.getLayers().get(i))) return false;
		}

		return true;
	}
}
