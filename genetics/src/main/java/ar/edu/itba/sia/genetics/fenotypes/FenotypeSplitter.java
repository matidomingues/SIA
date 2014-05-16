package ar.edu.itba.sia.genetics.fenotypes;

import org.jblas.ranges.Range;

import java.util.List;

public interface FenotypeSplitter {

	List<List<Allele>> split(Fenotype parent1, List<Range> splitPoints);
}
