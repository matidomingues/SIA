package ar.edu.itba.sia.genetics.operators.backpropagation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;

public class Backpropagator {
	private static final double BACKPROPAGATION_PROBABILITY=0.5;

	private final BackpropagationAlgorithm backpropagation;
	private final List<Pattern> patterns;

	public Backpropagator(BackpropagationAlgorithm backpropagation, List<Pattern> patterns) {
		this.backpropagation = backpropagation;
		this.patterns = Collections.unmodifiableList(patterns);
	}
	
	public Fenotype backpropagate(Fenotype f){
		
		if(doBackpropagation()){
			backpropagation.execute((PerceptronNetwork) f, patterns);
		}
		return f;
	}
	
	private boolean doBackpropagation(){
		Random random = new Random(System.nanoTime());
		double crossoverProb = random.nextDouble();
		return crossoverProb > BACKPROPAGATION_PROBABILITY;
	}
	
	
	
	
}
