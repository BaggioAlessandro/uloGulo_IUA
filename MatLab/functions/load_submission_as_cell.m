function [ submission ] = load_submission_as_cell( submission_filename )
%LOAD_SUBMISSION Loads a submission file as a cell array
%   Input format
%   First column is the user ID
%   Second column is an array with an ordered item recommendations
%   Output format
%   Cell array containing the recommendations for each user

% Load submission as matrix
submission_mat = load_submission(submission_filename);
% Convert it to cell array
submission = submission_mat2cell(submission_mat);

end

