package ar.edu.itba.sia.genetics.fenotypes.impl;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import org.jblas.ranges.Range;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkFenotypeSplitter implements FenotypeSplitter {

	public List<List<Allele>> split(Fenotype parent1, List<Range> splitPoints) {
		if (!(parent1 instanceof PerceptronNetwork)) {
			throw new IllegalArgumentException();
		}

		List<List<Allele>> splittedList = new ArrayList<List<Allele>>(
				splitPoints.size());
		List<Allele> originalAlleles = parent1.getAlleles();

		for (Range range : splitPoints) {
			List<Allele> subList = new ArrayList<Allele>(originalAlleles.size()/2);
			int from = range.value();
			for (int i = range.value(); range.hasMore(); range.next()) {
				subList.add(originalAlleles.get(i));
			}
			int to = range.value();
			range.init(from, to);
			if (!(subList == null || subList.isEmpty())) {
				splittedList.add(subList);
			}

		}

		return splittedList;
	}

}
