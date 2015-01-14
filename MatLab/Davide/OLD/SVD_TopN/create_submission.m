%load input data
[R, testUsers] = load_data('../../DataSet/train.csv', '../../DataSet/test.csv', 1);
[nUsers, nItems] = size(R);

%temporarily remove test users from the training matrix
R_no_test = R(setdiff(1:nUsers, testUsers), :);

%split the dataset into train and validation sets using hold_out (other
%techniques will follow ;) )
[trainMat, validationMat, validationUsers] = hold_out(R_no_test, 0.8, 5);

%extract relevant items from the validation set
expectedRelevant = keep_relevant(validationMat, 4);

%build the model
k = 200;
geModel = SVD_KNN(trainMat, k);

%compute scores for validation users
validationProfiles = trainMat(validationUsers,:);
geScores = SVD_KNN_scorer(geModel(validationUsers,2:size(geModel,2)), trainMat, validationProfiles, 1, k);
%geScores = SVD_KNN_scorerI(geModel, trainMat, validationProfiles, 1);
%then compute rankings
[geRanking, maRanking] = build_ranking(geScores);

%evaluate the RMSE over the predicted ratings
%rmse(validationMat, geScores)

%evaluate the MAP@K over the validation set
map_at_k(expectedRelevant, geRanking, 5)

%for you :-) : use above functions to generate rankings for the users in testUsers

