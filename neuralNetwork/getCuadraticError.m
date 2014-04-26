
function Em = getCuadraticError (weights,patterns,g)
    weightsQty = size(weights);
    patternsQty = size(patterns);
    totalLayers = weightsQty(1);
    Em=0;
    for k=1:patternsQty(1)
        pattern=[-1, patterns(k,1:end-1)];
        wishedOutput=pattern(1,end);
        aux=arrayfun(g,pattern*weights{1,1});
        for j=2:totalLayers
            aux=arrayfun(g,[-1 aux]*weights{j,1});
        end
        Em=Em+(wishedOutput-aux)^2;
    end
    Em=Em/patternsQty(1);
end
