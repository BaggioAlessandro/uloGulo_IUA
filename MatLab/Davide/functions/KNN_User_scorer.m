function [ scores ] = KNN_User_scorer(nn_model, rating_mat, profiles, exclude_already_rated, k)
%nn_model è la matrice con i k elementi più simili
%rating_mat è R
%profiles sono gli utenti da testare
%flag per escludere i film già votati dai risultati
%k è il numero di NN massimo di ogni utente
[n_users, ~] = size(nn_model);
%scores è di dimensione testUsers x items
scores = zeros(size(profiles));

for ii = 1:n_users
	%nn è la matrice dei k NN dell'i-esimo user da testare (k x item)
    nn = rating_mat(nn_model(ii,:),:);
    for jj=1:k
		%mm è la riga del j-esimo elemento più vicino
        mm = nn(jj,:);
		%peso i voti positivi di j in base alla sua distanza
        mm(mm>0) = k/(k+jj);
        nn(jj) = mm(1);
    end
	%il punteggio dei film è lo somma dei valori dei 200 film visti dai NN
    scores(ii,:) = sum(nn);
end

if exclude_already_rated ~= 0
    %for each user assign 0 score to each already rated item
    scores(profiles>0) = 0;
end


end