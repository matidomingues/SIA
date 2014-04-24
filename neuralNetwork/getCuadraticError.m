
function Em = getCuadraticError (weights,patterns,g)
    totalLayers=size(weights)(1);
    Em=0;
    for k=1:size(patterns)(1)
        pattern=[-1, patterns(k,1:end-1)];
        wishedOutput=pattern(1,end);
        aux=arrayfun(g,pattern*weights{1,1});
        for j=2:totalLayers
            aux=arrayfun(g,[-1 aux]*weights{j,1});
        endfor
        Em=Em+(wishedOutput-aux)^2;
    endfor
    Em=Em/2;
    
endfunction
