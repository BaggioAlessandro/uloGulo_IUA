function [ submission ] = load_submission( submission_filename )
%LOAD_SUBMISSION Loads a submission file as a matrix
%   Input format
%   First column is the user ID
%   Second column is an array with an ordered item recommendations
%   Output format
%   First column is the user ID
%   2nd to 6th columns item recommendations

% Always skip header
row_start = 1;
submission = dlmread(submission_filename, ',', row_start, 0);

end

