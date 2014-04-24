
%weightsHiddenLayer matriz de n1 x (e+1) (donde n1 es la cantidad de neuronas de la 1era capa oculta y e es la cantidad de entradas SIN contar el umbral, la columna 0 reperesenta el umbral).
%weightsLastLayer 
%n factor de aprendizaje (por ahora es fijo pero esto va a cambiar)
%patterns matriz de p x (e+1) (donde p es la cantidad de patrones y e es la cantidad de entradas SIN contar el umbral, y en la columna e+1-esima esta la salida deseada) 

function multiLayerPerceptron(weights,n,patterns,g,derivate,epsilon,epoques)
    Em=1;
    iterations=0;
    totalLayers=size(weights)(1);
    totalPatterns=size(patterns)(1);
    lowBound=1;
    do
      if(lowBound==totalPatterns){
       iterations++;
        lowBound=1;
      }  
      i=floor(rand(1)*(totalPatterns-lowBound)+lowBound);
        
      
    
    
    
    Em=getCuadraticError(weights,patterns,g);
    
    
    lowBounds++;
    auxiliar=patterns(i,:);
    patterns(i,:)=patterns(lowBounds,:);
    patterns(lowBounds,:)=patterns(i,:);
    
   while(Em>epsilon && iterations<=epoques )
   

   for i=1:size(patterns)(1) 
    pattern=[-1, patterns(i,1:end-1)];
    wishedOutput=patterns(i,end);
    auxi=weightsHiddenLayer*(pattern');
    V1=arrayfun(g,auxi);
    hM=weightsLastLayer*[-1, V1];
    sigma=arrayfun(g,hM);
    
    deltaoutput=(arrayfun(derivate,sigma))*(wishedOutput-sigma);
    deltaHidden=(arrayfun(derivate,V1))*();

   endfor
    
    
endfunction