package ar.edu.itba.sia.genetics.fenotypes.impl;

import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;

import ar.edu.itba.sia.genetics.fenotypes.Allele;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;

public class NeuralNetworkFenotype implements Fenotype {

	private static final double MAX_RANGE = 30.5;
	private static final double MIN_RANGE = -30.5;

	private int size = 0;
	private List<DoubleMatrix> layers = new ArrayList<DoubleMatrix>();
	
	/**
	 * arquitecture indica la cantidad de neuronas en cada capa (el primer elemento es la cantidad de entradas
	 * y el ultimo es la cantidad de salidas)
	 * @param arquitecture
	 */
	public NeuralNetworkFenotype(int[] arquitecture) {

		int layersQty = arquitecture.length - 1;

		for (int i = 0; i < layersQty - 1; i++) {
			DoubleMatrix matrix = DoubleMatrix.rand(arquitecture[i] + 1,
					arquitecture[i + 1]);
			matrix.mul((MAX_RANGE-MIN_RANGE)).add(MIN_RANGE);
			size+=(matrix.rows*matrix.columns);
			this.layers.add(matrix);
		}

	}
	
	public NeuralNetworkFenotype(int[] arquitecture,List<Allele> childAlleles){
		
		int layersQty = arquitecture.length - 1;

		for (int i = 0; i < layersQty - 1; i++) {
			DoubleMatrix matrix = DoubleMatrix.zeros(arquitecture[i] + 1,
					arquitecture[i + 1]);
			for(int row=0;row<matrix.rows;row++){
				for(int col=0;col<matrix.columns;col++){
					double value=childAlleles.get(size+row*matrix.columns+col).getAlleleValue();
					if(value<MIN_RANGE || value>MAX_RANGE){
						throw new IllegalArgumentException();
					}
					matrix.put(row, col,value);
				}
			}
			
			size+=(matrix.rows*matrix.columns);
			this.layers.add(matrix);
		}
		
		
	}

	
	public int size() {
		return size;
	}

	public double maxRange() {
		return MAX_RANGE;
	}

	public double minRange() {
		return MIN_RANGE;
	}

	public void alter(int locus, double value) {
		if(locus<0||locus>=size){
			return;
		}
		
		for(DoubleMatrix dm:layers){
			int lengthAleles=dm.rows*dm.columns;
			if(locus>lengthAleles){
				locus-=lengthAleles;
			}
			else if(locus>=0){
				int locusRow=locus/dm.columns;
				int locusColumn=locus-locusRow*dm.columns;
				dm.put(locusRow,locusColumn,value);
				return;
			}
			
		}

	}

}
