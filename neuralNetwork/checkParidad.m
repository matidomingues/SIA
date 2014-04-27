
function checkParidad (n,epsilon,epoques)
    auxiTime=now;
    %patterns=[1,1,0;0,1,1;1,0,1;0,0,0]
    patterns=[1,1,-1;-1,1,1;1,-1,1;-1,-1,-1]
    %patterns=[1,1,1,1;1-1,-1,1;-1,-1,-1,-1]
    weights = cell(3,1);
    weights{1,1}=rand(3,2);
    weights{2,1}=rand(3,2);
    weights{3,1}=rand(3,1);
    g = cell(3,2);
    g{1,1} = @SigmoideaFunction;
    g{1,2} = @derivateTanh;
    g{2,1} = @SigmoideaFunction;
    g{2,2} = @derivateTanh;
    g{3,1} = @SigmoideaFunction;
    g{3,2} = @derivateTanh;
    
    patternsQty = size(patterns);
    for i=1:patternsQty(1)
        patterns(i,1:end)
        answerMultiLayer(weights,patterns(i,1:end-1),g,patterns(i,end))
    end
    patterns
    timeElapsed=now-auxiTime
end
