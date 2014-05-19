package ar.edu.itba.sia.genetics.selectors;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.List;

public interface FenotypeSelector {
	List<Fenotype> select(List<Fenotype> fenotypes);
}
