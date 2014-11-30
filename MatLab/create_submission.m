%% Setup
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));

%load input data
[R, testUsers] = load_data('../DataSet/train.csv', '../DataSet/test.csv', 1);
[nUsers, nItems] = size(R);

%temporarily remove test users from the training matrix
R_no_test = R(setdiff(1:nUsers, testUsers), :);

%split the dataset into train and validation sets using hold_out (other
%techniques will follow ;) )
[trainMat, validationMat, validationUsers] = hold_out_fast(R_no_test, 0.8, 5);

%% SVD KNN model
%build the model
[geModel, geModelItem] = SVD_KNN(trainMat, 200, 38);

%compute scores for validation users
validationProfiles = trainMat(validationUsers,:);
geScores = SVD_KNN_scorer(geModel(validationUsers,2:size(geModel,2)), trainMat, validationProfiles, 1);

%% SVD Puro model
% Commented out as KNN is working better
%{
%build the model
params.shrinkage_items = 200;
params.shrinkage_users = 20;
geModel = global_effects_model(trainMat, params);
svdModel = SVD_Puro(trainMat, 80, geModel, 10);

%compute scores for validation users
validationProfiles = trainMat(validationUsers,:);
geScores = SVD_Puro_scorer(svdModel(validationUsers,:), trainMat, validationProfiles, 1);
%}

%% Compute metrics
%then compute rankings
[geRanking, maRanking] = build_ranking(geScores);

%evaluate the RMSE over the predicted ratings
%rmse(validationMat, geScores)

params.k = 5;
params.relevance_min_th = 4;
%evaluate the MAP@K over the validation set
map_at_k(validationMat, geRanking, params)

%for you :-) : use above functions to generate rankings for the users in testUsers

