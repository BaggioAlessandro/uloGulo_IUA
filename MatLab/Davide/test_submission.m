addpath(genpath('./functions'));
%load input data
[R, testUsers] = load_data('../../DataSet/train.csv', '../../DataSet/test.csv', 1);
[nUsers, nItems] = size(R);

%temporarily remove test users from the training matrix
R_no_test = R(setdiff(1:nUsers, testUsers), :);

%split the dataset into train and validation sets using hold_out (other
%techniques will follow)
[trainMat, validationMat, validationUsers] = hold_out(R_no_test, 0.8, 5);

%extract relevant items from the validation set
expectedRelevant = keep_relevant(validationMat, 4);

%build the model
K = 200;
[nnModel, nnModelDistance] = KNN_model(R, K, validationUsers);

%compute scores for validation users
validationProfiles = trainMat(validationUsers,:);
nnScores = KNN_User_scorer(nnModel(:,2:(K+1)), trainMat, validationProfiles, 1, K);

%then compute rankings
[nnRanking, ~] = build_ranking(nnScores);

%evaluate the MAP@K over the validation set
map_at_k(expectedRelevant, nnRanking, 5);