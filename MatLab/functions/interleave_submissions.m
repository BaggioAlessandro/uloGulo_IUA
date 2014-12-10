function [ submission ] = interleave_submissions( first_submission, second_submission)
%INTERLEAVE_SUBMISSIONS Takes an item from each submission until n
%   Takes one item at a time from each submission in the order of the
%   arguments, avoiding repeating them, until n items are included

firstRecommendations = first_submission(:,2:6);
secondRecommendations = second_submission(:,2:6);

% See http://en.wikipedia.org/wiki/Kronecker_product
combinedRecommendations = kron(firstRecommendations, [1,0]) + kron(secondRecommendations, [0,1]);

nTestUsers = size(combinedRecommendations,1);
submission = zeros(nTestUsers, 6);

for row_number = 1:nTestUsers
    submission(row_number,1) = first_submission(row_number,1);
    uniqueRecommendations = unique(combinedRecommendations(row_number, :), 'stable');
    submission(row_number,2:6) = uniqueRecommendations(1:5);
end

