package ar.edu.itba.sia.genetics.operators.crossers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import org.jblas.ranges.Range;

import java.util.*;

public class TwoPointCrossover extends Crossover {

	public TwoPointCrossover(FenotypeBuilder fenotypeBuilder, FenotypeSplitter splitter) {
		super(fenotypeBuilder, splitter);
	}

	@Override
	protected List<Range> getRanges(Fenotype parent1, Fenotype parent2) {
		Random random = new Random(System.nanoTime());
		int splitPoint1 = random.nextInt(parent1.size() - 1);
		int splitPoint2 = splitPoint1 + random.nextInt(parent1.size() - splitPoint1) + 1;
		return generateRanges(new int[]{0, splitPoint1, splitPoint2, parent1.size()});
	}
}
