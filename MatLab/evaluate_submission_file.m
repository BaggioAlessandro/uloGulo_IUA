%% Setup
% Submission to test
file_to_test = './submissions/thelast+ar.csv';
% 1 to skip header
row_start = 1;
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));

%% Test
% Correct output file
relevant_test = dlmread('../DataMisc/Test/Test_data/ordered_relevant_test.csv', ',', row_start, 0);
test_mat = dlmread(file_to_test, ',', row_start, 0);
% File with test user ids
testUsers = dlmread('../DataSet/test.csv', ',', row_start, 0);
% Group by user id as cell array
expected = arrayfun( @(id) relevant_test(relevant_test(:,1)==id, 2), testUsers, 'UniformOutput', 0 );
test = arrayfun( @(id) test_mat(test_mat(:,1)==id, [2 3 4 5 6]), testUsers, 'UniformOutput', 0 );
% evaluate using map_at_k
mapTest = map_at_k_relevant(expected, test, 5)