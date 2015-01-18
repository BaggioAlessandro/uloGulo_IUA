function expected = load_expected()
% Load the expected relevant items for test users

% Skip header
row_start = 1;
relevant_mat = dlmread('../DataMisc/Test/Test_data/ordered_relevant_test.csv', ',', row_start, 0);
testUsers = unique(relevant_mat(:,1));
expected = arrayfun( @(id) relevant_mat(relevant_mat(:,1)==id, 2), testUsers, 'UniformOutput', 0 );