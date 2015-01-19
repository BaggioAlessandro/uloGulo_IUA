function [ map ] = map_at_k_relevant(expected, actual, k)
%MAP_AT_K computes the Mean Average Precision at K for a given probe set.
% only relevant items in expected
n_probes = size(expected, 1);
average_precisions = zeros(n_probes, 1);
if iscell(actual)
    actual_array = actual;
else
    actual_array = submission_mat2cell(actual);
end

for pp=1:n_probes
   average_precisions(pp) = ap_at_k(expected{pp}, actual_array{pp}, k);
end

map = mean(average_precisions);

end

function [ ap ] = ap_at_k(expected, actual, k)
    relevant = ismember(actual(1:k), expected);
    precisions = (cumsum(relevant).*relevant)./(1:k);
    if(isempty(expected))
        ap = 1;
    else
        ap = sum(precisions)/k;
    end
end
