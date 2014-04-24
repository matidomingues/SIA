
function Em = getCuadraticError (weights,patterns,g)
    totalLayers=size(weights)(1);
    Em=0;
    for k=1:size(patterns)(1)
        pattern=[-1, patterns(k,1:end-1)];
        wishedOutput=pattern(1,end);
        aux=arrayfun(g,weights{1,1}*[-1 pattern]');
        for j=1:totalLayers
            aux=arrayfun(g,weights{j,1}*[-1 aux]');
        endfor
        Em=Em+(wishedOutput-aux)^2;
    endfor
    Em=Em/2;
    
endfunction
