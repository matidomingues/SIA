package ar.edu.itba.sia.genetics.operators.crossers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import org.jblas.ranges.Range;

import java.util.List;
import java.util.Random;


public class OnePointCrossover extends Crossover {

	public OnePointCrossover(FenotypeBuilder fenotypeBuilder, FenotypeSplitter splitter) {
		super(fenotypeBuilder, splitter);
	}

	@Override
	protected List<Range> getRanges(Fenotype parent1, Fenotype parent2) {
		Random random = new Random(System.nanoTime());
		int splitPoint = random.nextInt(parent1.size());
		return generateRanges(new int[]{0, splitPoint, parent1.size()});
	}
}
