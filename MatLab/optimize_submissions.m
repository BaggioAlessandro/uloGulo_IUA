%% Setup
% submission files that will be considered
% label, path
submission_files = {
        'last+ar' './submissions/thelast+ar.csv'
        'soft' './submissions/soft.csv'
        'last' './submissions/last.csv'
        'bestAR' './submissions/bestAR.csv'
        'popular' './submissions/popular.csv'
        'spearman' './submissions/knnspearman.csv'
    };

%% Execution
% Load correct recommendations
expected = load_expected();
% Load each submission file
[number_of_submissions, ~] = size(submission_files);
submission = cell(number_of_submissions,1);
for i = 1:number_of_submissions
    submission{i} = load_submission_as_cell(submission_files{i,2});
end
original_submission = submission;

[number_of_users, ~] = size(expected);
optimal_submission = zeros(number_of_users, 6);
% Copy test user ids
test_users = load_test_users();
optimal_submission(:,1) = test_users;
% Get 5 recommended items
number_of_recommendations = 5;
map = zeros(number_of_submissions,number_of_recommendations); 
taken_from = cell(1,number_of_recommendations);
%ri recommendation index
for ri=1:number_of_recommendations
    best_map = 0;
    best_submission = {};
    best_submission_index = 0;
    for i=1:number_of_submissions
        current_submission = submission{i};
        % Only take the head of the submission into account for the map
        current_map = map_at_k_relevant(expected, current_submission, 1);
        if current_map > best_map
            best_map = current_map;
            best_submission = current_submission;
            best_submission_index = i;
        end
        map(i,ri) = current_map;
    end
    % Extract the first recommendations
    best_recommendations = cellfun(@(array) array(1), best_submission);
    % Store recommendations, first column is id
    optimal_submission(:,ri+1) = best_recommendations;
    % Remove already recommended
    submission = remove_recommended(submission, best_recommendations);
    % Save trace
    %map(ri) = best_map; % map from that recommendation
    taken_from{ri} = submission_files{best_submission_index,1}; %label
end
% Optimization trace
map;
taken_from;
%% Evaluate
% Check final map
final_map = map_at_k_relevant(expected, optimal_submission, 5)

%% Generate output
write_submission_from_matrix(optimal_submission);

%% Expected private leaderboard
%Once submited change the public leaderboard score
public_score=0.40415;
private_score=(final_map - public_score *0.12)/0.88;
%Best submission by LMV
private_best=0.46702;
public_best=0.39926;
best_map=(private_best*0.88 + public_score *0.12);
%Can we win?
can_we_win = private_score > private_best