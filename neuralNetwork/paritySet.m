
function answer = paritySet (bitQty)
    auxiliar=generateparitySet(bitQty);
    
    for i=1:size(auxiliar)(1)
       
        if(mod(sum(auxiliar(i,:)'==1),2)==0)
            result=-1;
        else
            result=1;
        end
      answer(i,:)=[ auxiliar(i,:) result];  
    end
endfunction
