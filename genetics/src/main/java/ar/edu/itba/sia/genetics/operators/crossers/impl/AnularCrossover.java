package ar.edu.itba.sia.genetics.operators.crossers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import org.jblas.ranges.Range;

import java.util.List;
import java.util.Random;

public class AnularCrossover extends Crossover {

	public AnularCrossover(FenotypeBuilder fenotypeBuilder, FenotypeSplitter splitter, double probability) {
		super(fenotypeBuilder, splitter, probability);
	}

	@Override
	protected List<Range> getRanges(Fenotype parent1, Fenotype parent2) {
		Random random = new Random(System.nanoTime());
		int splitPoint1 = random.nextInt(parent1.size());
		int length = random.nextInt(parent1.size() / 2);
		int splitPoint2 = (splitPoint1 + length) % parent1.size();
		return generateRanges(new int[]{0, splitPoint1, splitPoint2, parent1.size()});
	}
}
