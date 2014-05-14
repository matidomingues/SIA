function [w crosstalk]=hopfield(patterns, unknown, toptime)
	N=size(patterns{1,1})(1)
	cantPatterns=size(patterns)(1);
	w=zeros(N,N);
	s=zeros(N,1);
	for i=1:cantPatterns
        pattern=patterns{i,1};
        w=w+pattern*(pattern');	
    end
    
	for i=1:N
        w(i,i)=0;	
    end
	w=w./N;
	s(:,1)=unknown;
	
	firstLoop=true;
	time=2;
	fixedPoint=false;
    same=0;
	while(firstLoop|| (time<=toptime && ~fixedPoint))
	    firstLoop=false;
	    
        randomNumber=floor(rand(1,1)*(N))+1;  
	  	
        s(:,time)=s(:,time-1);
        one=w(randomNumber,:);
        two=s(:,time-1);
        umbral=sum(w(randomNumber,:))/2;
        x=w(randomNumber,:)*s(:,time-1) - umbral; 
        if(x~=0)	
            s(randomNumber,time)=StepFunction(x);
            if((s(randomNumber,time)==s(randomNumber,time-1))&& same>=N)
              fixedPoint=true;  
            else
              same=same+1;
            end    
        end
        s;
        time=time+1;	
    end
	# crosstalk=zeros(N,N);
    # for v=1:N
        # for u=1:N
           # if(v~=u)
            # crosstalk(v,u);
           # end
            # 
        # end
        # 
    # end    
  	
    w=s(:,end);
    time
 end    