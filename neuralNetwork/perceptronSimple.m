#weigths es el vector con los pesos (w1 hasta wr) de las conexiones (donde w1 es el umbral
#n es el factor de aprendizaje
#patterns es una matriz con los patrones (en donde la primer columna representa el umbral, que es todo -1, 
#y la última columna es el resultado, y las del medio son las distintas entradas)

#La funcion perceptronSimple devuelve un vector con los pesos actualizados


function tableAns= perceptronSimple(weigths,n,patterns)
	tableAns=weigths;
	inputs=length(patterns(1,:))-1;  #cantidad de entradas
  patternsQty=length(patterns); #cantidad de patrones
	it=zeros(1,patternsQty);
  while(sum(it)/length(it)<1)  
    i=floor(rand(1)*(inputs)+1);
    it(i) = 1;
	   x=patterns(i,1:inputs)*(tableAns)';
		if (sign(x)!=patterns(i,end))
		   tableAns=correctWeights(tableAns,n,patterns(i,:));
        it=zeros(1,patternsQty);
    endif
  end
endfunction