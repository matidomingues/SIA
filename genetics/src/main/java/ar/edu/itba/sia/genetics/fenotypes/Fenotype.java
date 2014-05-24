package ar.edu.itba.sia.genetics.fenotypes;

import java.util.List;

public interface Fenotype {

	int size();
	double maxRange();
	double minRange();
	void alter(int locus);
	List<Allele> getAlleles();
}
