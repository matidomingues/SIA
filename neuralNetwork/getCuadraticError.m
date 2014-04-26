
function Em = getCuadraticError (patterns,O)
    patternsQty = size(patterns);
    totalLayers = size(O)(1);
    Em=0;
    for k=1:patternsQty(1)
        wishedOutput=patterns(k,end);
        output=O(1,k);
            
        Em=Em+((wishedOutput-output).^2);
    end
    Em=Em/2;
    
end
