%load input data
[R, testUsers] = load_data('train.csv', 'test.csv', 1);
[nUsers, nItems] = size(R);

%temporarily remove test users from the training matrix
R_no_test = R(setdiff(1:nUsers, testUsers), :);

%split the dataset into train and validation sets using hold_out (other
%techniques will follow ;) )
[trainMat, validationMat, validationUsers] = hold_out(R_no_test, 0.9, 5);

%extract relevant items from the validation set
expectedRelevant = keep_relevant(validationMat, 4);

%build the model
geModel = global_effects_model(trainMat);

%compute scores for validation users
validationProfiles = trainMat(validationUsers,:);
geScores = global_effects_scorer(geModel, validationUsers, validationProfiles, 1);

%then compute rankings
geRanking = build_ranking(geScores);

%evaluate the RMSE over the predicted ratings
rmse(validationMat, geScores)

%evaluate the MAP@K over the validation set
map_at_k(expectedRelevant, geRanking, 5)

%for you :-) : use above functions to generate rankings for the users in testUsers

