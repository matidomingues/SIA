
function z = minimizeTraneSet (traneSet, testSet,arquitecture,n,epsilon,epoques,g,derivateg,tolerance)
    totalLayers=size(arquitecture)(1)+1;
    outputQty=1;
    inputQty=(size(traneSet)(2)-outputQty)+1;
    
    weights = cell(totalLayers,1);
    weights{1,1}=rand(inputQty,arquitecture(1,1));
    for i=2:totalLayers-1  
        weights{i,1}=rand(arquitecture(i-1,1)+1,arquitecture(i,1));       
    end
    weights{totalLayers,1}=rand(arquitecture(end,1)+1,outputQty)
    
    learned=false;
    firstLoop=true;
    while(firstLoop || ~learned)
        firstLoop=false;
        
        weights=multiLayerPerceptron(weights,n,traneSet,g,derivateg,epsilon,epoques);
        
        testPatternsQty = size(testSet);
        wrongPatterns=[];
        j=1;
        for i=1:testPatternsQty(1)
            wishedOutput=testSet(i,end);
            z=answerMultiLayer(weights,testSet(i,1:end-1),g,wishedOutput);
            auxiliar=abs(z-wishedOutput)
            if(abs(z-wishedOutput)>epsilon)
                wrongPatterns(j)=i;
                j=j+1;
            end
        end
        errorPorcentage=size(wrongPatterns)(1)/testPatternsQty(1);
        if(errorPorcentage<=tolerance || testPatternsQty(1)==0)
            learned=true;
        else

            indexWrong=wrongPatterns(1);
            traneQty=size(traneSet)(1);
            traneSet(traneQty+1,:)=testSet(indexWrong,:);
            
            testSet(indexWrong,:)=testSet(testPatternsQty(1),:);
            testSet=testSet(1:testPatternsQty(1)-1,:);
        endif
    end
    z=traneSet
endfunction
