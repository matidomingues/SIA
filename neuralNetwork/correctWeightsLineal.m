
function z = correctWeightsLineal (weights,n,h,pattern)
    z=ones(1,length(weights)); 
  for i=1:length(weights)
    d= pattern(end)-h;
    z(i)=weights(i)+n*d*pattern(i);   
   end

endfunction
