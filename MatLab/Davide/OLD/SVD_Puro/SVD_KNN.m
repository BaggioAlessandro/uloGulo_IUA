function [model] = SVD_KNN(train_matrix, k, ge_model, kk)

%NRM = ge_model.global_avg + (ge_model.user_bias * ge_model.item_bias);
%tm = full(train_matrix);
%NRM(train_matrix>0)=0;
[U, S, V] = svds(train_matrix,kk);%tm+NRM);
A = U * sqrtm(S);
B = sqrtm(S) * V';

model = A*B;