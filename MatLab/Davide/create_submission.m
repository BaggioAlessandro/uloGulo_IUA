addpath(genpath('./functions'));
%load input data
[R, testUsers] = load_data('../../DataSet/train.csv', '../../DataSet/test.csv', 1);
[nUsers, nItems] = size(R);

%build the model
K = 200;
[nnModel, nnModelDistance] = KNN_model(R, K, testUsers);

%compute scores for validation users
testProfiles = R(testUsers,:);
%nnModel(:,2:(K+1)) prendo la matrice partendo dalla 2° colonna perchè la prima è l'elemento stesso
nnScores = KNN_User_scorer(nnModel(:,2:(K+1)), nnModelDistance(:,2:(K+1)), R, testProfiles, 1, K);

%then compute rankings
[nnRanking, maRanking] = build_ranking(nnScores);

write_submission_from_cells(nnRanking, testUsers, 'res.csv');
%csvwrite('rec.csv', maRanking);