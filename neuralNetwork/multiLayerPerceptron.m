
%weightsHiddenLayer matriz de n1 x (e+1) (donde n1 es la cantidad de neuronas de la 1era capa oculta y e es la cantidad de entradas SIN contar el umbral, la columna 0 reperesenta el umbral).
%weightsLastLayer 
%n factor de aprendizaje (por ahora es fijo pero esto va a cambiar)
%patterns matriz de p x (e+1) (donde p es la cantidad de patrones y e es la cantidad de entradas SIN contar el umbral, y en la columna e+1-esima esta la salida deseada) 

function answer=multiLayerPerceptron(weights,n,patterns,g,derivate,epsilon,epoques)
    Em=1;
    iterations=0;
    weightsSize = size(weights);
    totalLayers = weightsSize(1);
    hiddenLayers=totalLayers-1;
    V=cell(totalLayers,1);
    h=cell(totalLayers,1);
    delta=cell(totalLayers,1);
    patternsSize = size(patterns);
    totalPatterns = patternsSize(1);
    firstLoop = true;
    while (firstLoop || Em <= epsilon || iterations <= epoques)
        firstLoop = false;
        patternsOrder = randperm(totalPatterns);
        for i = 1:totalPatterns
            pattern=[-1, patterns(patternsOrder(i),1:end-1)];
            wishedOutput=patterns(patternsOrder(i),end);

            h{1,1}= pattern*weights{1,1};
            V{1,1}=[-1 arrayfun(g,h{1,1})];

            for j=2:totalLayers
                h{j,1}=V{j-1,1}*weights{j,1};
                aux=arrayfun(g,h{j,1});
                V{j,1}=[-1 aux] ;
            end

            V{totalLayers,1}=V{totalLayers,1}(1,2:end); %saco a la salida el umbral puesto de mas

            delta{totalLayers,1}= (arrayfun(derivate,h{totalLayers,1}))*(wishedOutput-V{totalLayers,1});

            for j=(totalLayers-1):-1:1  
                auxid=((delta{j+1,1})* weights{j+1,1}(2:end,:)');
                auxig=arrayfun(derivate,h{j,1});
                for t=1:length(auxid)
                    auxi(t)=auxid(1,t)*auxig(1,t);     
                end    
                delta{j,1}=auxi;     
            end  

            for j=2:totalLayers      
                weights{j,1}=weights{j,1}+n* ((V{j-1,1})'*delta{j,1});
            end  
            weights{1,1}=weights{1,1}+n* (pattern'*delta{1,1}); 

            Em=getCuadraticError(weights,patterns,g);
        end
        iterations = iterations + 1;
    end
    
    iterations
    Em
    answer=weights;
end