package ar.edu.itba.sia.genetics.selectors.impl;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteFenotypeSelector implements FenotypeSelector{

	private final FitnessFunction fitnessFunction;
	private final int k;
	
	public RouletteFenotypeSelector(int k, FitnessFunction fitnessFunction){
		this.fitnessFunction = fitnessFunction;
		this.k=k;
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		List<Fenotype> selectedList= new ArrayList<Fenotype>(k);
		double sumFitness=0;
		double[] averageFitnessAcumulated= new double[fenotypes.size()+1];
		
		int i=1;
		for(Fenotype f:fenotypes){
			averageFitnessAcumulated[i]=fitnessFunction.evaluate(f);
			sumFitness+=averageFitnessAcumulated[i];
			i++;
		}
		
		//averageFitness=sumFitness/fenotypes.size();
		averageFitnessAcumulated[0]=0;
		
		for(i=1;i<averageFitnessAcumulated.length;i++){
			averageFitnessAcumulated[i]=averageFitnessAcumulated[i]/sumFitness+averageFitnessAcumulated[i-1];
		}
				
		
		Random random = new Random(System.nanoTime());
		double[] r= new double[k];
		for(int j=0;j<k;j++){
			r[j]=random.nextDouble();
		}
		
		for(int j=0;j<k;j++){
			//System.out.println("j --"+j);
			
			boolean found=false;
			for(int l=1;l<(k+1) && !found;l++){
				double previous=averageFitnessAcumulated[l-1];
				double next=averageFitnessAcumulated[l];
				if(previous<=r[j] && r[j]<=next){
					//System.out.println("l --"+l);
					selectedList.add(fenotypes.get(l-1));
					found=true;
				}
			}
		}
		
		return selectedList;
	}

}
