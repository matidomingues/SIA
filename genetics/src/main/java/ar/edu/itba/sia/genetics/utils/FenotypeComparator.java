package ar.edu.itba.sia.genetics.utils;

import java.util.Comparator;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

public class FenotypeComparator implements Comparator<Fenotype> {

	public int compare(Fenotype o1, Fenotype o2) {
		
		return (int)(o1.fitnessFunction()-o2.fitnessFunction());
	}

}
