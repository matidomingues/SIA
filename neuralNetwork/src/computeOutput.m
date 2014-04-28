function [h, V] = computeOutput(totalLayers, pattern, weights, g)
	h = cell(totalLayers, 1);
	V = cell(totalLayers, 1);
    pattern;
    weights{1,1};
	h{1,1} = pattern * weights{1,1};
    V{1,1} = [-1 arrayfun(g{1,1},h{1,1})];
    
    for j=2:totalLayers
        h{j,1}=V{j-1,1}*weights{j,1};
        aux=arrayfun(g{j,1},h{j,1});
        V{j,1}=[-1 aux] ;
    end
    
    V{totalLayers,1}=V{totalLayers,1}(1,2:end); %saco a la salida el umbral puesto de mas
end            