% I got these files from our submissions in kaggle selecting
% the best files using different approaches
submission_1 = load_submission('./submissions/knn+AR.csv');
submission_2 = load_submission('./submissions/userKNN.csv');
% Optimal order
combination_order = [2 1 1 1 2 2 2 2 1 1];

combined_submission = combine_submissions(submission_1, submission_2, combination_order);

write_submission_from_matrix(combined_submission);
