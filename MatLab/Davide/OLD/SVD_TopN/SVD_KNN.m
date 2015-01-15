function [model] = SVD_KNN(train_matrix, k)
%-----------------------------------------
%[U, S, V] = svds(train_matrix, 38);
%%A = U * sqrtm(S);
%B = sqrtm(S) * V';

KNN = ExhaustiveSearcher(train_matrix,'Distance','spearman');
%%KNNI = ExhaustiveSearcher(B,'Distance','cosine');

%%[modelItem, ~] = knnsearch(KNNI, KNNI.X, 'K', 5);
[model, D] = knnsearch(KNN, KNN.X, 'K', k+1);
%-------------------------------------------
%KNN = ExhaustiveSearcher(B','Distance','spearman');
%[model, D] = knnsearch(KNN, KNN.X, 'K', k+1);