function submission = remove_recommended( submission, recommended )
%REMOVE_RECOMMENDED Remove the recommended items from each user for each
%submission
%   Submissions are cell arrays and the recommended items a vector
number_of_users = length(submission{1});
number_of_submissions = length(submission);
for i=1:number_of_submissions
    current_submission = submission{i};
    for j=1:number_of_users
        % Items for this user in the submission
        current_user_items = current_submission{j};
        % Recommended item
        recommended_item = recommended(j);
        % Remove recommended
        current_submission{j} = current_user_items(current_user_items ~= recommended_item);
    end
    % Save result
    submission{i} = current_submission;
end

end

