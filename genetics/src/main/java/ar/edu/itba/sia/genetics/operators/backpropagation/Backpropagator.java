package ar.edu.itba.sia.genetics.operators.backpropagation;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;

import java.util.List;
import java.util.Random;

public class Backpropagator {

	private final double backpropagationProbability;
	private final BackpropagationAlgorithm backpropagation;
	private final List<Pattern> patterns;

	public Backpropagator(BackpropagationAlgorithm backpropagation, List<Pattern> patterns, double backpropagationProbability) {
		this.backpropagation = backpropagation;
		this.patterns = patterns;
		this.backpropagationProbability = backpropagationProbability;
	}
	
	public Fenotype backpropagate(Fenotype f){
		
		if(doBackpropagation()){
			return forceBackpropagation(f);
		}
		return f;
	}

	public Fenotype forceBackpropagation(Fenotype f) {
		backpropagation.execute((PerceptronNetwork) f, patterns);
		return f;
	}
	
	private boolean doBackpropagation(){
		Random random = new Random(System.nanoTime());
		double crossoverProb = random.nextDouble();
		return crossoverProb < backpropagationProbability;
	}

	public BackpropagationAlgorithm getBackpropagation() {
		return backpropagation;
	}
	
}
