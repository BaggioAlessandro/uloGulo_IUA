function [ submission ] = combine_submissions( first_submission, second_submission, combination_order)
%COMBINE_SUBMISSIONS Takes an item from each submission as in the order,
%keeping only the unique items and limiting the number of them to 5.
%   first_submission and second_submission must have the following format
%       column 1:       User Id
%       column 2 to 6:  Recommended Items Ids
%   combination_order is a mask that indicates from which submission to get
%       each position in the combine recommendation
%   Example:
%   first_submission = [1 11 12 13 14 15;2 15 12 13 11 14];
%   second_submission = [1 21 22 23 24 25; 2 12 21 14 13 22];
%   combination_order = [1 1 1 1 2 1 2 2 2 2];
%   submission = combine_submissions(first_submission,second_submission,combination_order)
%   %submission =
%   %  1    11    12    13    14    21
%   %  2    15    12    13    11    14

first_recommendations = first_submission(:,2:end);
second_recommendations = second_submission(:,2:end);
masks={zeros(5,10),zeros(5,10)};
takenCount = [0;0];
for i = 1:size(combination_order,2)
    takeFrom = combination_order(i);
    nextPosition = takenCount(takeFrom) + 1;
    masks{takeFrom}(nextPosition,i) = 1;
    takenCount(takeFrom) = nextPosition;    
end

combinedRecommendations = first_recommendations * masks{1} + second_recommendations * masks{2};

nTestUsers = size(combinedRecommendations,1);
submission = zeros(nTestUsers, 6);

for row_number = 1:nTestUsers
    submission(row_number,1) = first_submission(row_number,1);
    uniqueRecommendations = unique(combinedRecommendations(row_number, :), 'stable');
    submission(row_number,2:6) = uniqueRecommendations(1:5);
end

