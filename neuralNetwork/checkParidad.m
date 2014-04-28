
function checkParidad (n,epsilon,epoques)
    auxiTime=now();
    load samples2.txt;
    patterns = samples2;
    patternsQty = size(patterns);
    training_patterns_qty = 100;
    test_patterns_qty = patternsQty(1) - training_patterns_qty;
    training_patterns = samples2(1:training_patterns_qty,:);
    test_patterns = samples2(training_patterns_qty + 1:end, :);
    %patterns=[1,1,0;0,1,1;1,0,1;0,0,0]
    %patterns=[1,1,-1;-1,1,1;1,-1,1;-1,-1,-1]
    %patterns=[1,1,1,1;1-1,-1,1;-1,-1,-1,-1]
    weights = cell(2,1);
    weights{1,1}=rand(3,3);
    weights{2,1}=rand(4,1);
    g = cell(2,2);
    g{1,1} = @SigmoideaFunction;
    g{1,2} = @derivateTanh;
    g{2,1} = @LinealFunction;
    g{2,2} = @derivateLineal;

    weights=multiLayerPerceptron(weights,n,test_patterns,g,epsilon,epoques);
    memorization_percentage = 0;
    for i = 1:training_patterns_qty
        patterns(i,1:end);
        if (answerMultiLayer(weights, patterns(i,1:end-1), g, patterns(i,end)) <= epsilon)
            memorization_percentage = memorization_percentage + 1;
        end
    end
    learning_percentage = 0;
    for i=training_patterns_qty + 1:patternsQty
        patterns(i,1:end);
        if (answerMultiLayer(weights,patterns(i,1:end-1),g,patterns(i,end)) <= epsilon)
            learning_percentage = learning_percentage + 1;
        end
    end
    patterns;
    memorization_percentage = memorization_percentage / training_patterns_qty
    learning_percentage = learning_percentage / test_patterns_qty
    timeElapsed=now()-auxiTime
    sound([-1:0.1:1]);
end
