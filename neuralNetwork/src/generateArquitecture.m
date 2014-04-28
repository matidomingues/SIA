% 
function [weights g]= generateArquitecture (arquitecture, expectedOutputs, traningSet, testSet, hiddenLayersActivationFunction, hiddenLayersActivationFunctionDerivate, finalLayerActivationFunction, finalLayerActivationFunctionDerivate)

	arquitectrueSize = size(arquitecture);
    totalLayers= arquitectrueSize(1) + 1;
    trainingSetSize = size(traningSet);
    inputQty=(trainingSetSize(2)-expectedOutputs)+1;
    
    weights = cell(totalLayers,1);
    weights{1,1}=rand(inputQty,arquitecture(1,1));
    for i=2:totalLayers-1  
        weights{i,1}=rand(arquitecture(i-1,1)+1,arquitecture(i,1));       
    end
    weights{totalLayers,1}=rand(arquitecture(end,1) + 1, expectedOutputs);

    g = cell(totalLayers, 1);
    for i = 1:totalLayers - 1
    	g{i,1} = hiddenLayersActivationFunction;
    	g{i,2} = hiddenLayersActivationFunctionDerivate;
    end
    g{totalLayers, 1} = finalLayerActivationFunction;
    g{totalLayers, 2} = finalLayerActivationFunctionDerivate;
end
