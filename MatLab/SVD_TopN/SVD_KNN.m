function [model, modelItem] = SVD_KNN(train_matrix, k, kk)

[U, S, V] = svds(train_matrix, kk);
A = U * sqrtm(S);
B = sqrtm(S) * V';

KNN = ExhaustiveSearcher(A,'Distance','cosine');
KNNI = ExhaustiveSearcher(B,'Distance','cosine');

[modelItem, ~] = knnsearch(KNNI, KNNI.X, 'K', 5);
[model, D] = knnsearch(KNN, KNN.X, 'K', k+1);