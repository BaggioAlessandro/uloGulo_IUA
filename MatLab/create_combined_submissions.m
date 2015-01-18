%% Setup
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));
% I got these files from our submissions in kaggle selecting
% the best files using different approaches
knn_submission = load_submission('./submissions/soft.csv');
ar_submission = load_submission('./submissions/thelast+ar.csv');
%combination_order = [1 1 1 1 1 2 2 2 2 2];
%combination_order = [1 2 1 2 1 2 1 2 1 2];
%combination_order = [2 2 2 2 2 1 1 1 1 1];
combination_order = [2 1 1 2 2 2 2 1 1 1];

combined_submission = combine_submissions(knn_submission, ar_submission, combination_order);

write_submission_from_matrix(combined_submission);

%% Evaluation
expected = load_expected();
map = map_at_k_relevant(expected, combined_submission, 5)

%% Expected private leaderboard
%Once submited change the public leaderboard score
%public_score=0.39504;
%private_score=(map - public_score *0.12)/0.88;
