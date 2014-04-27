
function checkParidad (n,epsilon,epoques)
    auxiTime=time;
    patterns=[1,1,-1;-1,1,1;1,-1,1;-1,-1,-1]
    %patterns=[1,1,1,1;1-1,-1,1;-1,-1,-1,-1]
    weights = cell(3,1);
    weights{1,1}=rand(3,2);
    weights{2,1}=rand(3,2);
    weights{3,1}=rand(3,1);
    weights=multiLayerPerceptron(weights,n,patterns,@SigmoideaFunction,@derivateTanh,epsilon,epoques);
    
    patternsQty = size(patterns);
    for i=1:patternsQty(1)
        patterns(i,1:end)
        answerMultiLayer(weights,patterns(i,1:end-1),@SigmoideaFunction,patterns(i,end))
    end
    patterns
    timeElapsed=time-auxiTime
end
