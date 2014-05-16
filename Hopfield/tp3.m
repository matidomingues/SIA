function z=tp3(toptime)
    row=64;
    cols=64;
    patterns=cell(3,1);
    patterns{1,1}= convertfromimage(imread("images/a.png"))';
    patterns{2,1}= convertfromimage(imread("images/f.png"))';
    patterns{3,1}= convertfromimage(imread("images/h.png"))';
    test1=(patterns{1,1})';
    test1(1,1:500)=zeros(1,500);
    imwrite(converttoimage(test1,row,cols),'noiseA1.jpg');
    pattern1=hopfield(patterns,test1,toptime);
    imwrite(converttoimage((-1)*patterns{1,1},row,cols),'inverse1.jpg');
    imwrite(converttoimage(pattern1,row,cols),'pattern1.jpg');
     pattern2=hopfield(patterns,patterns{2,1},toptime);
     imwrite(converttoimage(pattern2,row,cols),'pattern2.jpg');
    pattern3=hopfield(patterns,convertfromimage(imread("images/circle2.png"))',toptime);
    imwrite(converttoimage(pattern3,row,cols),'pattern3.jpg');
    
end

function pattern=convertfromimage(a)
    auxi=reshape(a',1,prod(size(a)));
    pattern=2*auxi-1;
end  

function imageAns=converttoimage(vector,row,cols)
    auxi=(reshape(vector,row,cols))';
    imageAns=(auxi+1)/2;
end    