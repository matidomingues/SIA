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

	public Crossover(FenotypeBuilder fenotypeBuilder, FenotypeSplitter splitter) {
		this.fenotypeBuilder = fenotypeBuilder;
		this.splitter = splitter;
	}

	public Set<Fenotype> crossover(Fenotype parent1, Fenotype parent2) {
		Set<Fenotype> children = new HashSet<Fenotype>();

		if (parent1.size() != parent2.size()) {
			throw new IllegalArgumentException("Locus count mismatch.");
		}
		if (parent1.size() < 1) {
			throw new IllegalArgumentException("Small fenotype.");
		}

		List<Range> ranges = getRanges(parent1, parent2);

		List<List<Allele>> parent1Alleles = splitter.split(parent1, ranges);
		List<List<Allele>> parent2Alleles = splitter.split(parent2, ranges);

		children.add(mixParents(parent1Alleles, parent2Alleles));
		children.add(mixParents(parent2Alleles, parent1Alleles));

		return children;
	}

	protected abstract List<Range> getRanges(Fenotype parent1, Fenotype parent2);

	protected List<Range> generateRanges(int[] rangePoints) {
		if (rangePoints.length < 2) throw new IllegalArgumentException("Too few split points.");
		Arrays.sort(rangePoints);
		List<Range> ranges = new ArrayList<Range>(rangePoints.length - 1);
		for (int i = 0; i < rangePoints.length; i++) {
			ranges.add(new IntervalRange(rangePoints[i - 1], rangePoints[i]));
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

}