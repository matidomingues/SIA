package ar.edu.itba.sia.genetics.operators.crossers.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import org.jblas.ranges.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class UniformCrossover extends Crossover {

	private final float probability;

	public UniformCrossover(FenotypeBuilder fenotypeBuilder, FenotypeSplitter splitter, float probability) {
		super(fenotypeBuilder, splitter);
		this.probability = probability;
	}

	@Override
	protected List<Range> getRanges(Fenotype parent1, Fenotype parent2) {
		Random random = new Random(System.nanoTime());
		List<Integer> rangePointsList = new LinkedList<Integer>();
		boolean passed = true;
		rangePointsList.add(0);
		for (int i = 1; i < parent1.size() - 1; i++) {
			if (random.nextFloat() > probability) {
				if (!passed) {
					rangePointsList.add(i);
				}
				passed = true;
			} else {
				if (passed) {
					rangePointsList.add(i);
				}
				passed = false;
			}
		}
		rangePointsList.add(parent1.size());
		int[] rangePoints = new int[rangePointsList.size()];
		int j = 0;
		for(Integer i : rangePointsList) {
			rangePoints[j] = i;
			j++;
		}
		return generateRanges(rangePoints);
	}
}
