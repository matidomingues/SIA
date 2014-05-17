package ar.edu.itba.sia.genetics.fenotypes.impl;

import java.util.ArrayList;
import java.util.List;

import org.jblas.ranges.IntervalRange;
import org.jblas.ranges.Range;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;

public class NeuralNetworkFenotypeSplitter implements FenotypeSplitter {

	public List<List<Allele>> split(Fenotype parent1, List<Range> splitPoints) {
		if (!(parent1 instanceof NeuralNetworkFenotype)) {
			throw new IllegalArgumentException();
		}

		NeuralNetworkFenotype fenotype = (NeuralNetworkFenotype) parent1;

		List<List<Allele>> splittedList = new ArrayList<List<Allele>>(
				splitPoints.size());
		List<Allele> originalAlleles = fenotype.getAlleleList();

		for (Range range : splitPoints) {
			int from = range.value();
			int to = range.value();
			while (range.hasMore()) {
				range.next();
				to = range.value();
			}

			List<Allele> subList = originalAlleles.subList(from, to);
			if (!(subList == null || subList.isEmpty())) {
				splittedList.add(subList);
			}

		}

		return splittedList;
	}

}
