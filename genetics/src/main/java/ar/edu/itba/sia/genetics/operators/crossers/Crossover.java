package ar.edu.itba.sia.genetics.operators.crossers;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;

import org.jblas.ranges.IntervalRange;
import org.jblas.ranges.Range;

import java.util.*;

public abstract class Crossover {
	private final FenotypeBuilder fenotypeBuilder;
	private final FenotypeSplitter splitter;
	private final double probability;

	public Crossover(FenotypeBuilder fenotypeBuilder, FenotypeSplitter splitter, double probability) {
		this.fenotypeBuilder = fenotypeBuilder;
		this.splitter = splitter;
		this.probability = probability;
	}

	public List<Fenotype> crossover(Fenotype parent1, Fenotype parent2) {
		List<Fenotype> children = new ArrayList<Fenotype>();

		if (parent1.size() != parent2.size()) {
			throw new IllegalArgumentException("Locus count mismatch.");
		}
		if (parent1.size() < 1) {
			throw new IllegalArgumentException("Small fenotype.");
		}
		if(doCrossover()){
		List<Range> ranges = getRanges(parent1, parent2);
		
		List<List<Allele>> parent1Alleles = splitter.split(parent1, ranges);
		List<List<Allele>> parent2Alleles = splitter.split(parent2, ranges);

		children.add(mixParents(parent1Alleles, parent2Alleles));
		children.add(mixParents(parent2Alleles, parent1Alleles));
		}
		else{
		 children.add(parent1);
		 children.add(parent2);
		}
		return children;
	}

	protected abstract List<Range> getRanges(Fenotype parent1, Fenotype parent2);

	protected List<Range> generateRanges(int[] rangePoints) {
		if (rangePoints.length < 2) throw new IllegalArgumentException("Too few split points.");
		Arrays.sort(rangePoints);
		List<Range> ranges = new ArrayList<Range>(rangePoints.length - 1);
		for (int i = 1; i < rangePoints.length; i++) {
			Range range = new IntervalRange(rangePoints[i - 1], rangePoints[i]);
			range.init(rangePoints[i - 1], rangePoints[i]);
			ranges.add(range);
		}
		return ranges;
	}

	private Fenotype mixParents(List<List<Allele>> parent1Alleles, List<List<Allele>> parent2Alleles) {
		List<Allele> childAlleles = new LinkedList<Allele>();
		for (int i = 0; i < parent1Alleles.size(); i++) {
			if (i % 2 == 0) {
				childAlleles.addAll(parent1Alleles.get(i));
			} else {
				childAlleles.addAll(parent2Alleles.get(i));
			}
		}
		return fenotypeBuilder.build(childAlleles);
	}

	public FenotypeSplitter getSplitter() {
		return this.splitter;
	}

	public FenotypeBuilder getFenotypeBuilder() {
		return this.fenotypeBuilder;
	}
	
	public boolean doCrossover(){
		Random random = new Random(System.nanoTime());
		double crossoverProb=random.nextDouble(); 
		return crossoverProb < probability;
	}

}
