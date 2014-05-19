package ar.edu.itba.sia.genetics.fenotypes;

public interface Fenotype {

	int size();
	double maxRange();
	double minRange();
	void alter(int locus);
	double fitnessFunction();
}
