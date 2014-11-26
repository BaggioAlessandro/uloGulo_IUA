function [ scores ] = SVD_KNN_scorer(ge_model, train_mat, profiles, exclude_already_rated)

[n_users, ~] = size(ge_model);
scores = zeros(size(profiles));
for ii = 1:n_users
    nn = train_mat(ge_model(ii,:),:);
    nn(nn>0) = 1;
    scores(ii,:) = sum(nn);
end

%model.user_bias(test_users) is used to take the user_bias for each test
%user

if exclude_already_rated ~= 0
    %for each user assign 0 score to each already rated item
    scores(profiles>0) = 0;
end


end