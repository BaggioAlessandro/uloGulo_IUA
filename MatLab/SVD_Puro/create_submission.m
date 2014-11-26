%load input data
[R, testUsers] = load_data('../../DataSet/train.csv', '../../DataSet/test.csv', 1);
[nUsers, nItems] = size(R);

%temporarily remove test users from the training matrix
R_no_test = R(setdiff(1:nUsers, testUsers), :);

%split the dataset into train and validation sets using hold_out (other
%techniques will follow ;) )
[trainMat, validationMat, validationUsers] = hold_out(R_no_test, 0.7, 5);

%extract relevant items from the validation set
expectedRelevant = keep_relevant(validationMat, 4);

%build the model
params.shrinkage_items = 200;
params.shrinkage_users = 10;
%geModel = global_effects_model(trainMat, params);
svdModel = SVD_KNN(trainMat, 80, geModel, 10);

%compute scores for validation users
validationProfiles = trainMat(validationUsers,:);
geScores = SVD_KNN_scorer(svdModel(validationUsers,:), trainMat, validationProfiles, 1);

%then compute rankings
[geRanking, maRanking] = build_ranking(geScores);

%evaluate the RMSE over the predicted ratings
rmse(validationMat, geScores)

%evaluate the MAP@K over the validation set
map_at_k(expectedRelevant, geRanking, 5)

%for you :-) : use above functions to generate rankings for the users in testUsers

