%% Setup
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));
% I got these files from our submissions in kaggle selecting
% the best files using different approaches
ar_submission = load_submission('./submissions/bestAR.csv');
knn_submission = load_submission('./submissions/bestKNN.csv');
combination_order = [2 1 1 1 1 1 2 2 2 2];

combined_submission = combine_submissions(knn_submission, ar_submission, combination_order);

write_submission_from_matrix(combined_submission);