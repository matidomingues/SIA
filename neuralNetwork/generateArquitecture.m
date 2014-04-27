
function weights = generateArquitecture (arquitecture, traneSet,testSet)
    totalLayers=size(arquitecture)(1)+1;
    outputQty=1;
    inputQty=(size(traneSet)(2)-outputQty)+1;
    
    weights = cell(totalLayers,1);
    weights{1,1}=rand(inputQty,arquitecture(1,1));
    for i=2:totalLayers-1  
        weights{i,1}=rand(arquitecture(i-1,1)+1,arquitecture(i,1));       
    end
    weights{totalLayers,1}=rand(arquitecture(end,1)+1,outputQty)

endfunction
