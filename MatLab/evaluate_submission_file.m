%% Setup
% Submission to test
file_to_test = './submissions/thelast+ar.csv';

%% Test
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));
% Correct output file
expected = load_expected();
test = load_submission(file_to_test);
% evaluate using map_at_k
mapTest = map_at_k_relevant(expected, test, 5)