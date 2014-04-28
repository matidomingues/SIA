function main(inputFileName, trainingSetQty, arquitecture, expectedOutputs, n, epsilon, epoques)
	patterns = load(inputFileName, '-ascii');
	patternsQty = size(patterns);
    testPatternsQty = patternsQty(1) - trainingSetQty;
    trainingPatterns = patterns(1:trainingSetQty,:);
    testPatterns = patterns(trainingSetQty + 1:end, :);

    hiddenLayersActivationFunction = @SigmoideaFunction;
    hiddenLayersActivationFunctionDerivate = @derivateTanh;
    finalLayerActivationFunction = @LinealFunction;
    finalLayerActivationFunctionDerivate = @derivateLineal;
    
    [weights g] = generateArquitecture(arquitecture, expectedOutputs, trainingPatterns, testPatterns, hiddenLayersActivationFunction, hiddenLayersActivationFunctionDerivate, finalLayerActivationFunction, finalLayerActivationFunctionDerivate);

    weights=multiLayerPerceptron(weights,n,trainingPatterns,g,epsilon,epoques);

    memorizationPercentage = 0;
    for i = 1:trainingSetQty
        patterns(i,1:end);
        if (answerMultiLayer(weights, patterns(i,1:end-1), g, patterns(i,end)) <= epsilon)
            memorizationPercentage = memorizationPercentage + 1;
        end
    end
    learningPercentage = 0;
    for i=trainingSetQty + 1:patternsQty
        patterns(i,1:end);
        if (answerMultiLayer(weights,patterns(i,1:end-1),g,patterns(i,end)) <= epsilon)
            learningPercentage = learningPercentage + 1;
        end
    end
    memorizationPercentage = memorizationPercentage / trainingSetQty
    learningPercentage = learningPercentage / testPatternsQty
    timeElapsed=now()-auxiTime
end