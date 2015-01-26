%% Setup
% Submission to test
file_to_test = './submissions/svd.csv';

%% Test
% Add functions folder to path to avoid call errors
addpath(genpath('./functions'));
% Correct output file
expected = load_expected();
test = load_submission(file_to_test);
% evaluate using map_at_k
map_at_5 = map_at_k_relevant(expected, test, 5)

%% map profile
mapi = zeros(1,5);
for i = 1:5
    mapi(i) = map_at_k_relevant(expected, test(:,[1 i+1]), 1);
end
mapacc = zeros(1,5);
for i = 1:5
    mapacc(i) = map_at_k_relevant(expected, test, i);
end

%% Plot
plot_matrix = [map_at_5 * ones(1,5); mapi; mapacc]';

figure;
h = plot(plot_matrix);
h(2).Marker = '*';
h(3).Marker = 'o';
legend('map@5','map(i)@1','map@i');
title(strcat('map profile for ', file_to_test));
xlabel('i')
ylabel('map')