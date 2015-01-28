function [NN, NND] = KNN_model(rating_matrix, k, val_users)
%-----------------------------------------
%Questo è come si usa un SVD con Sk 38x38
%[U, S, V] = svds(rating_matrix, 38);
%A = U * sqrtm(S);
%B = sqrtm(S) * V';
%-----------------------------------------

%Creo l'oggetto per il cacolo della distanza. spearman sembra dare risultati migliori di cosine
KNN = ExhaustiveSearcher(rating_matrix,'Distance','spearman');

[NN, NND] = knnsearch(KNN, KNN.X(val_users, :), 'K', k+1);

%----------------------------------------------------
% se si vuole calcolare l'item similarity:
%KNN = ExhaustiveSearcher(rating_matrix', 'Distance', 'spearman');
%[NN, NND] = knnsearch(KNN, KNN.X, 'K', k+1);
%----------------------------------------------------