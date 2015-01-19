%% Setup
% submission files that will be considered
submission_files = {
        './submissions/soft.csv'
        './submissions/thelast+ar.csv'
    };

%% Execution
% Load correct recommendations
expected = load_expected();
% Load each submission file
number_of_submissions = length(submission_files);
submission = cell(size(submission_files));
for i = 1:number_of_submissions
    submission{i} = load_submission_as_cell(submission_files{i});
end
original_submission = submission;

map = zeros(1,number_of_submissions);
best_map = 0;
best_submission = {};
[number_of_users, ~] = size(expected);
optimal_submission = zeros(number_of_users, 6);
% Copy test user ids
test_users = load_test_users();
optimal_submission(:,1) = test_users;
for i=1:number_of_submissions
    current_submission = submission{i};
    current_map = map_at_k_relevant(expected, current_submission, 1);
    if current_map > best_map
        best_map = current_map;
        best_submission = current_submission;
    end
    map(i) = current_map;
end
% Extract the first recommendations
best_recommendations = cellfun(@(array) array(1),best_submission);
optimal_submission(:,2) = best_recommendations;
% Remove already recommended
submission = remove_recommended(submission, best_recommendations);
%Result
map