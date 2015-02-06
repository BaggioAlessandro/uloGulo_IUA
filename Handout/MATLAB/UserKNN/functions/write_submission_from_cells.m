function write_submission_from_cells( rankingCells, testUsers, outputFile )
%write_submission_from_cells Generates a csv file from the build_ranking output
%   Uses the code from nearfxx on the Forum
%   Output the ranking to a file.
mat =(cell2mat(rankingCells));
fid=fopen(outputFile,'wt');
[rows,~]=size(mat);
fprintf(fid,'UserId,RecommendedMovieIds\n');
for i=1:rows
    fprintf(fid,'%d,%d %d %d %d %d\n',testUsers(i,1), mat(i,1), mat(i,2), mat(i,3), mat(i,4), mat(i,5));
end
fclose(fid);

end

