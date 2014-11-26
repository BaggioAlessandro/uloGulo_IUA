function [model] = SVD_KNN(train_matrix, k)

[U, S, V] = svds(train_matrix);
A = U * sqrtm(S);
%B = sqrtm(S) * V';

KNN = ExhaustiveSearcher(A,'Distance','cosine');

[model, D] = knnsearch(KNN, KNN.X, 'K', k+1);