package genetics.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jblas.DoubleMatrix;

public class BackpropagationAlgoritm {

	private static double LEARNING_ALPHA = 0.0001;
	private static double LEARNING_BETA = 0.2;
	private static double ALPHA = 0.75;
	private static int ERRORLOOKBACK = 100;

	public void execute(List<DoubleMatrix> weights,List<DoubleMatrix> patterns,double n, double epsilon,int epoques,boolean useMomentum, boolean useEthaAdaptative){
		double Em=1;
	    int iterations=0;
	    int delayer = 0;
	    int adaptativeK=5;
	    double learningN=n;
	    adaptativeArr = cell(adaptativeK,2);
	    adaptativeArr(:,:) = 0;
	    int totalLayers = weights.size();
	    int hiddenLayers=totalLayers-1;
	    List<DoubleMatrix> V=new ArrayList<DoubleMatrix>(totalLayers);
	    List<DoubleMatrix> h=new ArrayList<DoubleMatrix>(totalLayers);
	    List<DoubleMatrix> oldDWeights = new ArrayList<DoubleMatrix> (totalLayers);
//	    for(int i=0;i<totalLayers;i++){
//	    	weightSize = size(weights{i,1});
//	    	oldDWeights{i, 1} = zeros(weightSize(1), weightSize(2));    	
//	    }
	   
	    List<DoubleMatrix> delta=new ArrayList<DoubleMatrix>(totalLayers);
	    int totalPatterns = patterns.size();
	    int totalLengthPattern=patterns.get(0).getColumns();
	    
	    boolean firstLoop = true;
	        
	        double[] EmHistory = double [];
	        int EmHistorySize = 0;
	        boolean keepGoing = true;
	        
	        do{
	        	Collections.shuffle(patterns);
	        	
	        	for(int i=0;i<totalPatterns;i++){
	                    DoubleMatrix pattern = patterns.get(i);
	                    double wishedOutput=pattern.get(totalLengthPattern);
	                    [h V] = computeOutput(totalLayers, pattern, weights, g);
	                    
	                    delta{totalLayers,1}= (arrayfun(g{totalLayers,2},h{totalLayers,1}))*(wishedOutput-V{totalLayers,1});
	                    
	                    for(int j=(totalLayers-1);j>=1;j--){

	                    	auxid=((delta.get(j+1)* weights{j+1,1}(2:end,:)');
	                    			auxig=arrayfun(g{j,2},h{j,1});
	                    	auxi=[];
	                    	for t=1:length(auxid)
	                    			auxi(t)=auxid(t)*auxig(t);     
	                    	end    
	                    	delta{j,1}=auxi;     
	                    	
	                    }
	                    
	                    for(int j=2;j<totalLayers;j++){
	                        dWeight = learningN * ((V{j-1,1})' * delta{j,1});
	                        weights{j,1}=weights{j,1} + dWeight + alpha .* oldDWeights{j,1};
	                        if(useMomentum){
	                        	oldDWeights{j,1} = dWeight;
	                        	
	                        };
	                    };
	                    weights{1,1}=weights{1,1}+learningN* (pattern'*delta{1,1});
	        	}
	        	
	        	
	        	
	        	
	        }while (keepGoing &&  (Em >= epsilon && iterations < epoques));
	
	
	
	
	}
}
