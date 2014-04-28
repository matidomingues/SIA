% main function to use our multilayer perceptron
% Arguments are:
% 	inputFileName: Filename for the input file in ASCII containing the patterns. It should contain the inputs and the
%		expected outputs in the mentioned order.
%	trainingSetQty: The number of patterns from the file to be used as a training set.
%	architecture: An array expressing the number of perceptron for each hidden layer.
%	n: The learning factor.
%	epsilon: The expected error.
%	epoques: The number of epochs to be run.
% Example:
% 	main('samples2.txt', 100, [5], 1, .2, .001, 5000)
% In the shown example the inputs are taken from the 'samples2.txt' file, using 100 patterns as trainingSet. 
% The remaining patterns will be used as test patterns. This architecture will use 5 perceptrons in the hidden layer.
% The output will be of only 1 value. Learning factor will be of .2, maximum expected error will be of .001 and will
% iterate along 5000 epochs.

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

    auxiTime = now;
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