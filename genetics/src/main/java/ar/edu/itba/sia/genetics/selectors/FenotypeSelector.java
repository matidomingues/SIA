package ar.edu.itba.sia.genetics.selectors;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.Set;

public interface FenotypeSelector {
	Set<Fenotype> select(Set<Fenotype> fenotypes);
}
