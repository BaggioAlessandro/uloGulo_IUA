%% Setup
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));
% I got these files from our submissions in kaggle selecting
% the best files using different approaches
submission_1 = load_submission('./submissions/pureKNNwithLikes.csv');
submission_2 = load_submission('./submissions/thelast+ar.csv');
%combination_order = [1 1 1 1 1 2 2 2 2 2];
%combination_order = [1 2 1 2 1 2 1 2 1 2];
%combination_order = [2 2 2 2 2 1 1 1 1 1];
%combination_order = [2 1 2 1 2 1 2 1 2 1];
% Optimal found by trial and error
% best so far
combination_order = [2 1 1 1 2 2 2 2 1 1];
% Optimal according script
%combination_order = [2 1 1 2 2 2 2 1 1 1];

combined_submission = combine_submissions(submission_1, submission_2, combination_order);

write_submission_from_matrix(combined_submission);

%% Evaluation
expected = load_expected();
combination_order
map = map_at_k_relevant(expected, combined_submission, 5)

%% Expected private leaderboard
%Once submited change the public leaderboard score
%public_score=0.40543;
%private_score=(map - public_score *0.12)/0.88;
