
%weightsHiddenLayer matriz de n1 x (e+1) (donde n1 es la cantidad de neuronas de la 1era capa oculta y e es la cantidad de entradas SIN contar el umbral, la columna 0 reperesenta el umbral).
%weightsLastLayer 
%n factor de aprendizaje (por ahora es fijo pero esto va a cambiar)
%patterns matriz de p x (e+1) (donde p es la cantidad de patrones y e es la cantidad de entradas SIN contar el umbral, y en la columna e+1-esima esta la salida deseada) 

function multiLayerPerceptron(weights,n,patterns,g,derivate,epsilon,epoques)
    Em=1;
    iterations=0;
    totalLayers=size(weights)(1);
    hiddenLayers=totalLayers-1;
    V=cell(totalLayers,1);
    h=cell(totalLayers,1);
    delta=cell(totalLayers,1);
    totalPatterns=size(patterns)(1);
    lowBound=1;
    do
      if(lowBound==totalPatterns){
       iterations++;
        lowBound=1;
      }  
      i=floor(rand(1)*(totalPatterns-lowBound)+lowBound);
      
      pattern=[-1, patterns(i,1:end-1)];
      wishedOutput=patterns(i,end);
      aux=pattern;
      for j=1:totalLayers
        aux=aux*(weights{j,1})';
        h{j,1}=aux;
        aux=arrayfun(g,aux);
        V{j,1}=[-1 aux];
        
      endfor  
  
      output=arrayfun(g,weights{totalLayers,1}*V{totalLayers,1});  
      delta{totalLayers,1}= (arrayfun(derivate,h{totalLayers,1}))*(wishedOutput-V{totalLayers,1}); 
      
      for j=1:(totalLayers-1)  
       delta{j,1}=arrayfun(derivate,h{j,1}) * (delta{j+1,1})' * weights{j,1};     
      endfor  

      for j=1:totalLayers      
        weights{j,1}=weights{j,1}+n*((delta{j,1})'*V{j,1});
      endfor  

      Em=getCuadraticError(weights,patterns,g);
    
    
      lowBounds++;
      auxiliar=patterns(i,:);
      patterns(i,:)=patterns(lowBounds,:);
      patterns(lowBounds,:)=patterns(i,:);
    
   while(Em>epsilon && iterations<=epoques )
    
endfunction