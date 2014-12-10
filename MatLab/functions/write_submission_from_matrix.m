function write_submission_from_matrix( recommendations )
%write_submission_from_matrix Generates a csv file from a plain matrix
%   Input matrix format, first column user id, 2:6 columns ordered item ids
%   Output the ranking to a file.
mat = recommendations;
fid=fopen('submission.csv','wt');
[rows,~]=size(mat);
fprintf(fid,'UserId,RecommendedMovieIds\n');
for i=1:rows
    fprintf(fid,'%d,%d %d %d %d %d\n',mat(i,1), mat(i,2), mat(i,3), mat(i,4), mat(i,5), mat(i,6));
end
fclose(fid);

end

