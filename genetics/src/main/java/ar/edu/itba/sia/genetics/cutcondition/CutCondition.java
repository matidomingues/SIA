package ar.edu.itba.sia.genetics.cutcondition;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.List;

public interface CutCondition {
	boolean conditionMet(List<Fenotype> fenotypes);
}
