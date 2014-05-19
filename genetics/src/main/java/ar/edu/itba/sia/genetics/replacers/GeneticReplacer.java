package ar.edu.itba.sia.genetics.replacers;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.List;

public interface GeneticReplacer {
	void replace(List<Fenotype> fenotypes, List<Fenotype> selected);
}
