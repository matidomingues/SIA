
function z = correctWeights (weights,n,pattern)
  z=ones(1,length(weights)); 
  for i=1:length(weights)
    z(i)=weights(i)+2*n*pattern(end)*pattern(i);   
   end
endfunction
