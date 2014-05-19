package ar.edu.itba.sia.genetics.selectors.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;

public class UniversalFenotypeSelector implements FenotypeSelector {

private int k;
	
	public UniversalFenotypeSelector(int k){
		this.k=k;
	}
	
	public List<Fenotype> select(List<Fenotype> fenotypes) {
		//List<Fenotype> array=new ArrayList<Fenotype>(fenotypes);
		//HashSet<Fenotype> selectedSet=new HashSet<Fenotype>(k);
		List<Fenotype> selectedList= new ArrayList<Fenotype>(k);
		
		double sumFitness=0,averageFitness=0;
		double[] averageFitnessAcumulated= new double[fenotypes.size()+1];
		
		
		for(Fenotype f:fenotypes){
			sumFitness+=f.fitnessFunction();
		}
		
		averageFitness=sumFitness/fenotypes.size();
		averageFitnessAcumulated[0]=0;
		
		int i=1;
		for(Fenotype f:fenotypes){
			averageFitnessAcumulated[i]=averageFitnessAcumulated[i-1]+(f.fitnessFunction()/averageFitness);
			i++;
		}
				
		
		Random random = new Random(System.nanoTime());
		double rmain=random.nextDouble();
		double[] r= new double[k];
		for(int j=0;j<k;j++){
			r[j]=(rmain+(j+1)-1)/k;
		}
		
		for(int j=0;j<k;j++){
			boolean found=false;
			for(int l=1;l<(k+1) && !found;l++){
				if(averageFitnessAcumulated[i-1]<=r[j] && r[j]<=averageFitnessAcumulated[i]){
					selectedList.add(fenotypes.get(l-1));
					found=true;
				}
			}
		}
		
		return selectedList;
	}

}
