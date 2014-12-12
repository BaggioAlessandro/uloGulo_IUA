%% Setup
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));
% I got these files from our submissions in kaggle selecting
% the best files using different approaches
ar_submission = load_submission('./submissions/bestAR.csv');
knn_submission = load_submission('./submissions/bestKNN.csv');

combined_submission = interleave_submissions(knn_submission, ar_submission);

write_submission_from_matrix(combined_submission);