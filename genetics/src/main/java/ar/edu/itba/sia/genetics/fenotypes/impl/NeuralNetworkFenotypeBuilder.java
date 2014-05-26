package ar.edu.itba.sia.genetics.fenotypes.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.perceptrons.Layer;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.TanhMatrixFunction;
import ar.edu.itba.sia.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

public class NeuralNetworkFenotypeBuilder implements FenotypeBuilder{

	private final int[] architecture;
	private final List<MatrixFunction> transferenceFunctions;
	private final int sizeArchitecture;
	
	public NeuralNetworkFenotypeBuilder(int[] arquitecture, List<MatrixFunction> transferenceFunctions){
		this.architecture =arquitecture;
		this.transferenceFunctions = Collections.unmodifiableList(transferenceFunctions);
		int sizeArchitecture = 0;
		for (int i = 0; i < (arquitecture.length - 1); i++) {
			sizeArchitecture += ((arquitecture[i] + 1) * (arquitecture[i + 1]));
		}
		this.sizeArchitecture = sizeArchitecture;
	}

	public Fenotype build() {
		List<Layer> layers = new ArrayList<Layer>(architecture.length - 2);

		for (int i = 1; i < architecture.length; i++) {
			layers.add(new Layer(DoubleMatrix.rand(architecture[i],architecture[i-1] + 1), transferenceFunctions.get(i-1)));
		}

		return new PerceptronNetwork(layers);
	}
	
	public Fenotype build(List<Allele> childAlleles) {
		if(childAlleles.size() != sizeArchitecture)
			throw new IllegalArgumentException(String.format("Invalid number of alleles. Recieved %d expected %d.", childAlleles.size(), sizeArchitecture));

		Iterator<Allele> childAllelesIt = childAlleles.iterator();
		List<Layer> layers = new ArrayList<Layer>(architecture.length - 2);
		int i = 1;
		while (childAllelesIt.hasNext()) {
			Layer l = buildLayer(childAllelesIt, i);
			layers.add(l);
			i++;
		}
		
		return new PerceptronNetwork(layers);
	}

	private Layer buildLayer(Iterator<Allele> childAllelesIt, int i) {
		double[] values = new double[architecture[i] * (architecture[i-1] + 1)];
		for (int j = 0; j < values.length && childAllelesIt.hasNext(); j++) {
			values[j] = childAllelesIt.next().getValue();
		}
		return new Layer(new DoubleMatrix(architecture[i], architecture[i-1] + 1, values),
				transferenceFunctions.get(i - 1));
	}

	public Fenotype clone(Fenotype f){
		PerceptronNetwork p=(PerceptronNetwork)f;
		return new PerceptronNetwork(p.getLayers());

	}
}
