#perceptronSimpleAnswer es la red neuronal que ya aprendio el problema
#y que ahora, dado un problema, da la respuesta

# weights son los pesos de las conexiones (calculados en perceptronSimple), 
#donde el primer elemento es el bias (siempre vale -1) y el resto son las entradas

#pattern es un vector en donde el primer elemento es el bias (que siempre debe valer -1) y el resto son las entradas comunes
function z = perceptronSimpleAnswer (weights, pattern)
    inputs=length(pattern);  #cantidad de entradas
    x=pattern(1:inputs)*weights';
    if(x>=0)
        z=1;
    else
        z=-1;
    endif
endfunction
