
function z = correctWeightsSigmoidea (weights,n,h,pattern)
  z=ones(1,length(weights)); 
  for i=1:length(weights)
    gderivative=1 - tanh(h);
    d=(pattern(end)-tanh(h))*gderivative;
    z(i)=weights(i)+n*d*pattern(i);
    
  end

endfunction
