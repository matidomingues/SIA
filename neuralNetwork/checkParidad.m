

function checkParidad (n,epsilon,epoques)
    patterns=[1,1,-1;-1,1,1;1,-1,1;-1,-1,-1]
    %patterns=[1,1,1,1;1-1,-1,1;-1,-1,-1,-1]
    weights = cell(2,1);
    weights{1,1}=[rand(1,2); rand(1,2); rand(1,2)];
    weights{2,1}=[rand(1,3)]'
    weights=multiLayerPerceptron(weights,n,patterns,@SigmoideaFunction,@derivateTanh,epsilon,epoques);
    
    patternsQty = size(patterns);
    for i=1:patternsQty(1)
        patterns(i,1:end)
        answerMultiLayer(weights,patterns(i,1:end-1),@SigmoideaFunction)
    end
    patterns
end
