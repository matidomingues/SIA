
function Em = getCuadraticError (patterns,O)
    patternsQty = size(patterns);
    Osize = size(O);
    totalLayers = Osize(1);
    Em=0;
    for k=1:patternsQty(1)
        wishedOutput=patterns(k,end);
        output=O{k};
            
        Em= Em + ((wishedOutput-output).^2);
    end
    Em=Em/2;
    
end
