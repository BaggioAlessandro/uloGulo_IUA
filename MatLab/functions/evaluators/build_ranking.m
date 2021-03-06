function [ ranking, ranking_mat ] = build_ranking( scores )
%BUILD_RANKING converts scores produced by a scorer into a ranking stored
%into a cell array

ranking = cell(size(scores,1),1);
ranking_mat = zeros(size(scores,1),5);
%for each user profile, store the column indices (i.e., the user ids) 
%sorted according to their rating in descending order
for ss = 1:size(scores,1)
    [~, ranking{ss}] = sort(scores(ss,:),'descend');
    ranking_mat(ss,:) = ranking{ss}(1:5);
end

end

