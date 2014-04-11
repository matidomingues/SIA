
function z = perceptronSimpleAnswer (weights, pattern)
    inputs=length(pattern)-1;  #cantidad de entradas
    x=pattern(1:inputs)*weights';
    if(x>=0)
        z=1;
    else
        z=-1;
    endif
endfunction
