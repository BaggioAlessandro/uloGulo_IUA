function [ scores ] = global_effects_scorer(ge_model, test_users, profiles, exclude_already_rated)
%GLOBAL_EFFECTS_SCORER returns scores for all test_users for a given
%ge_model. profiles contains the rating vectors of each user in test_users.

if nargin < 4
    exclude_already_rated = 0;
end

n_users = length(test_users);  
n_items = length(ge_model.item_bias);

%model.user_bias(test_users) is used to take the user_bias for each test
%user
scores = ge_model.global_avg + repmat(full(ge_model.item_bias), [n_users 1]) ...
    + repmat(full(ge_model.user_bias(test_users)), [1 n_items]);

if exclude_already_rated ~= 0
    %for each user assign 0 score to each already rated item
    scores(profiles>0) = 0;
end


end

