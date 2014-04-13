#weigths es el vector con los pesos (w1 hasta wr) de las conexiones (donde w1 es el umbral
#n es el factor de aprendizaje
#patterns es una matriz con los patrones (en donde la primer columna representa el umbral, que es todo -1, 
#y la última columna es el resultado, y las del medio son las distintas entradas)

#La funcion perceptronSimple devuelve un vector con los pesos actualizados


function tableAns= perceptronSimple(weigths,n,patterns)
	tableAns=weigths;
	inputs=length(patterns(1,:))-1;  #cantidad de entradas
  patternsQty=size(patterns)(1); #cantidad de patrones
	it=zeros(1,patternsQty);
  while(sum(it)/length(it)<1)  
  %for i=1:patternsQty
    i=floor(rand(1)*(patternsQty)+1);
    
	   x=patterns(i,1:inputs)*(tableAns)';
		if ((x>=0)!=(patterns(i,end)>=0))
		   tableAns=correctWeights(tableAns,n,patterns(i,:));
        it=zeros(1,patternsQty);
    else
        it(i)=1;
    endif
   end
  
     
    sum(it)/length(it)
endfunction