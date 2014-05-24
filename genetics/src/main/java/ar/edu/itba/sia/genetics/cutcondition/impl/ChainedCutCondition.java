package ar.edu.itba.sia.genetics.cutcondition.impl;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

import java.util.List;

public class ChainedCutCondition implements CutCondition {

	private final List<CutCondition> conditionList;

	public ChainedCutCondition(List<CutCondition> conditionList) {
		this.conditionList = conditionList;
	}

	@Override
	public boolean conditionMet(List<Fenotype> fenotypes) {
		boolean conditionMet = false;
		for(CutCondition cc : conditionList) {
			conditionMet &= cc.conditionMet(fenotypes);
			if (conditionMet) break;
		}
		return conditionMet;
	}
}
