#weigths es el vector con los pesos (w1 hasta wr) de las conexiones (donde w1 es el umbral
#n es el factor de aprendizaje
#patterns es una matriz con los patrones (en donde la primer columna representa el umbral, que es todo -1, 
#y la �ltima columna es el resultado, y las del medio son las distintas entradas)

#g es la funcion de activacion
#La funcion perceptronSimple devuelve un vector con los pesos actualizados


function tableAns= perceptronSimple(weigths,n,patterns,g,correctWeights)
    tableAns=weigths;
	inputs=length(patterns(1,:))-1;  #cantidad de entradas
    patternsQty=size(patterns)(1); #cantidad de patrones
  
    do
        patternsOrder = randperm(patternsQty);
        for i = 1:patternsQty
          x = patterns(patternsOrder(i), 1:inputs) * (tableAns)';
          while (g(x) ~= patterns(patternsOrder(i), end)) 
            tableAns = correctWeights(tableAns, n, x, patterns(patternsOrder(i), :));
            x = patterns(patternsOrder(i), 1:inputs) * (tableAns)';
          end
        end
        learnt = true;
        for i = 1:patternsQty
          x = patterns(patternsOrder(i), 1:inputs) * (tableAns)';
          if (g(x) ~= patterns(1, end)) 
            learnt = false;
            break;
          end
        end
    until (learnt)
 endfunction