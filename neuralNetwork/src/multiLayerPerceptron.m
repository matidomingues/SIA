
%weightsHiddenLayer matriz de n1 x (e+1) (donde n1 es la cantidad de neuronas de la 1era capa oculta y e es la cantidad de entradas SIN contar el umbral, la columna 0 reperesenta el umbral).
%weightsLastLayer 
%n factor de aprendizaje (por ahora es fijo pero esto va a cambiar)
%patterns matriz de p x (e+1) (donde p es la cantidad de patrones y e es la cantidad de entradas SIN contar el umbral, y en la columna e+1-esima esta la salida deseada) 

function [answer EmHistory] =multiLayerPerceptron(weights,n,patterns,g,epsilon,epoques)
    Em=1;
    iterations=0;
    adaptativeK=5;
    errorLookBack = 100;
    learningN=n;
    learningAlpha = 0.01;
    learningBeta = 0.01;
    adaptativeArr = cell(adaptativeK,2);
    adaptativeArr(:,:) = 0;
    weightsSize = size(weights);
    totalLayers = weightsSize(1);
    hiddenLayers=totalLayers-1;
    V=cell(totalLayers,1);
    h=cell(totalLayers,1);
    oldDWeights = cell(totalLayers, 1);
    for i = 1:totalLayers
        weightSize = size(weights{i,1});
        oldDWeights{i, 1} = zeros(weightSize(1), weightSize(2));
    end
    delta=cell(totalLayers,1);
    patternsSize = size(patterns);
    totalPatterns = patternsSize(1);
    firstLoop = true;
    useMomentum = true;
    useAdaptative = true;
    alpha = .75;
    EmHistory = [];
    EmHistorySize = 0;
    keepGoing = true;
    while (keepGoing && (firstLoop || (Em >= epsilon && iterations < epoques)))
        firstLoop = false;
        patternsOrder = randperm(totalPatterns);
        for i = 1:totalPatterns
            pattern = patterns(patternsOrder(i),:);
            wishedOutput=patterns(patternsOrder(i),end);
            [h V] = computeOutput(totalLayers, patterns(patternsOrder(i),:), weights, g);
            
            delta{totalLayers,1}= (arrayfun(g{totalLayers,2},h{totalLayers,1}))*(wishedOutput-V{totalLayers,1});
            
            for j=(totalLayers-1):-1:1
                auxid=((delta{j+1,1})* weights{j+1,1}(2:end,:)');
                auxig=arrayfun(g{j,2},h{j,1});
                auxi=[];
                for t=1:length(auxid)
                    auxi(t)=auxid(t)*auxig(t);     
                end    
                delta{j,1}=auxi;     
            end

            for j=2:totalLayers
                dWeight = learningN * ((V{j-1,1})' * delta{j,1});
                weights{j,1}=weights{j,1} + dWeight + alpha .* oldDWeights{j,1};
                if (useMomentum)
                    oldDWeights{j,1} = dWeight;
                end
            end
            weights{1,1}=weights{1,1}+learningN* (pattern'*delta{1,1});
        end
        O=cell(totalLayers,1);
        for i=1:patternsSize(1)
            [h V] = computeOutput(totalLayers, patterns(i,:), weights, g);

            O{i,1}=V{totalLayers,1}(1,:); 
        end
        

        Em=getCuadraticError(patterns,O);
        EmHistory = [EmHistory Em];
        EmHistorySize = EmHistorySize + 1;
        if (EmHistorySize > errorLookBack + 1) 
            stagnant = false;
            if (abs(EmHistory(EmHistorySize) - EmHistory(EmHistorySize - errorLookBack)))
                stagnant = true;
            end
            if (stagnant) 
                stagnant = false;
                for i = EmHistorySize:-1:EmHistorySize - errorLookBack
                    if (abs(EmHistory(i) - EmHistory(i - 1)) < (epsilon / 10))
                        stagnant = true;
                    end
                end
            end
            if (stagnant) 
                keepGoing = input('Se estancó el error... seguimos?');
                if (keepGoing == false) 
                    EmHistorySize = 0;
                end
                break;
            end
        end
        if (useAdaptative) 
          for(j=2:adaptativeK)
            adaptativeArr{j-1, 1} = adaptativeArr{j,1};
            adaptativeArr{j-1, 2} = adaptativeArr{j,2};
          end
          adaptativeArr{adaptativeK,1} = Em;
          adaptativeArr{adaptativeK,2} = weights;
          positive = false;
          adaptativeArr{:,1}
          for i=1:adaptativeK-1    
            if (adaptativeArr{i,1} != 0 && ((adaptativeArr{i+1,1} - adaptativeArr{i,1}) > 0))
                "positive"
                for w=1:adaptativeK
                    if(adaptativeArr{w,1} != 0)
                        weights = adaptativeArr{w,2};
                        break;
                    end
                end
                adaptativeArr(:,:) = 0;
                learningN = learningN - learningBeta*n;
                positive = true;
                break;
            end
          end
          if(positive == false)
            for i=1: adaptativeK
                if(adaptativeArr{i,1} == 0)
                    break;
                end
            end
            if(i == adaptativeK)
                "negative"
                learningN = learningN+learningAlpha;
            end  
         end
        end
        learningN
        iterations = iterations + 1;
    end
    
    iterations
    Em
    answer=weights;
end