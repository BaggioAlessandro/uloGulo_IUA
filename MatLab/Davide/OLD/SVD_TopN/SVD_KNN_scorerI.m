function [ scores ] = SVD_KNN_scorerI(ge_model, train_mat, profiles, exclude_already_rated)

[n_users, ~] = size(profiles);
scores = zeros(size(profiles))+1;
for ii = 1:n_users
    user_row = profiles(ii,:);
    item_rated = find(user_row);
    for jj=1:5
        item_relevance = ge_model(item_rated(jj),:);
        for qq=1:200
            scores(ii,item_relevance(1,qq)) =scores(ii,item_relevance(1,qq)) * 200/(200+qq); 
        end
    end
    scores(scores==1) = 0;
end

%model.user_bias(test_users) is used to take the user_bias for each test
%user

if exclude_already_rated ~= 0
    %for each user assign 0 score to each already rated item
    scores(profiles>0) = 0;
end


end