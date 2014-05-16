package ar.edu.itba.sia.genetics.replacers;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.Set;

public interface GeneticReplacer {
	void replace(Set<Fenotype> fenotypes, Set<Fenotype> selected);
}
