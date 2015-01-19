function test_users = load_test_users()
% Load the test user ids

% Skip header
row_start = 1;
test_filename = '../DataSet/test.csv';
%Read the test users and keep it as a column vector
test_users = dlmread(test_filename, ',', row_start, 0);