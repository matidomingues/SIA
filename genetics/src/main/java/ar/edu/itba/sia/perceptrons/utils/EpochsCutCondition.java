package ar.edu.itba.sia.perceptrons.utils;


import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;
import ar.edu.itba.sia.perceptrons.backpropagation.CutCondition;

public class EpochsCutCondition implements CutCondition{

	private final int maxEpochs;

	public EpochsCutCondition(int maxEpochs) {
		this.maxEpochs = maxEpochs;
	}

	@Override
	public boolean conditionMet(BackpropagationAlgorithm backpropagationAlgorithm) {
		return backpropagationAlgorithm.getEpochs() <= maxEpochs;
	}
}
