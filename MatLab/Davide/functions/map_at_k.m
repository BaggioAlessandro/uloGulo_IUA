function [ map ] = map_at_k(expected, actual, k)
%MAP_AT_K computes the Mean Average Precision at K for a given probe set.
n_probes = size(expected, 1);
average_precisions = zeros(n_probes, 1);

for pp=1:n_probes
   average_precisions(pp) = ap_at_k(expected{pp}, actual{pp}, k);
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
