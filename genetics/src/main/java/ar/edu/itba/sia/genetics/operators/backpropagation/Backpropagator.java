package ar.edu.itba.sia.genetics.operators.backpropagation;

import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

public class Backpropagator {
	private static final double BACKPROPAGATION_PROBABILITY=0.5;
	
	
	public Fenotype backpropagate(Fenotype f){
		
		if(doBackpropagation()){
			//TODO
			
		}
		else{
			return f;
		}
		return null;
	}
	
	private boolean doBackpropagation(){
		Random random = new Random(System.nanoTime());
		double crossoverProb=random.nextDouble(); 
		return crossoverProb>BACKPROPAGATION_PROBABILITY;
	}
	
	
	
	
}
