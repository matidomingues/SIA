function answer=generateparitySet(bitQty)
    answer=zeros(2^bitQty,bitQty);
    j=1;
    if(bitQty<=1)
        answer=[-1;1];
      else
        auxi=generateparitySet(bitQty-1);
        for i=1:length(auxi)
         answer(j,:)=[-1 auxi(i,:)];
         j=j+1;
         answer(j,:)=[1 auxi(i,:)];
         j=j+1;
        end    
    end
    
end    