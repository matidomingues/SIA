package ar.edu.itba.sia.perceptrons.backpropagation;

import ar.edu.itba.sia.perceptrons.PerceptronNetwork;

public interface CutCondition {

	boolean conditionMet(BackpropagationAlgorithm backpropagationAlgorithm);
}
