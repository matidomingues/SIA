function Z=answerMultiLayer(weights,pattern,g,wishedOutput)
    weightsQty = size(weights);
    totalLayers= weightsQty(1);
    hiddenLayers=totalLayers-1;
    pattern = [-1 pattern];
    [h V] = computeOutput(totalLayers, pattern, weights, g);
    Z=wishedOutput-V{totalLayers,1};
end