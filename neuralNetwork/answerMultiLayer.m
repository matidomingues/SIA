function z=answerMultiLayer(weights,pattern,g,wishedOutput,minVal)
    weightsQty = size(weights);
    totalLayers= weightsQty(1);
    hiddenLayers=totalLayers-1;
    V=cell(totalLayers,1);
    h=cell(totalLayers,1);
    realPattern=[-1 pattern];
    
    h{1,1}= realPattern*weights{1,1};
    V{1,1}=[minVal arrayfun(g,h{1,1})];

    for j=2:totalLayers
        h{j,1}=V{j-1,1}*weights{j,1};
        aux=arrayfun(g,h{j,1});
        V{j,1}=[minVal aux]; 
    end
    V{totalLayers,1}=V{totalLayers,1}(1,2:end); %saco a la salida el umbral puesto de mas
    V{totalLayers,1}
    Z=wishedOutput-V{totalLayers,1}

    h{1,1}= realPattern*weights{1,1};
    V{1,1}=[-1 arrayfun(g,h{1,1})];

    for j=2:totalLayers
        h{j,1}=V{j-1,1}*weights{j,1};
        aux=arrayfun(g,h{j,1});
        V{j,1}=[-1 aux]; 
    end
    V{totalLayers,1}=V{totalLayers,1}(1,2:end); %saco a la salida el umbral puesto de mas
    z= V{totalLayers,1}
    Z=wishedOutput-V{totalLayers,1}
end