
function z = correctWeightsLinealSigmoidea (weights,n,sigma,pattern)
    z=ones(1,length(weights)); 
  for i=1:length(weights)
    z(i)=weights(i)+n*(1-pattern(end)*x)(pattern(end)*pattern(i));   
   end

endfunction
