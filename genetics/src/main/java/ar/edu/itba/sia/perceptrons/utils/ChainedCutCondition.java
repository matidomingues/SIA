package ar.edu.itba.sia.perceptrons.utils;

import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;
import ar.edu.itba.sia.perceptrons.backpropagation.CutCondition;

import java.util.List;

public class ChainedCutCondition implements CutCondition {

	private final List<CutCondition> cutConditions;

	public ChainedCutCondition(List<CutCondition> cutConditions) {
		this.cutConditions = cutConditions;
	}

	@Override
	public boolean conditionMet(BackpropagationAlgorithm backpropagationAlgorithm) {
		boolean conditionMet = true;

		for (CutCondition cc: cutConditions) {
			conditionMet = conditionMet&& cc.conditionMet(backpropagationAlgorithm);
			//if (conditionMet) break;
		}
		return conditionMet;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
