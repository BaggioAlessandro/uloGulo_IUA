function cell_submission = submission_mat2cell( mat_submission )
%SUBMISSION_MAT2CELL Transform matricial output format to cell array
%   from a n_usersx6 matrix to a n_users length cell_array
    cell_submission = arrayfun( @(id) mat_submission(mat_submission(:,1)==id, [2:end]), mat_submission(:,1), 'UniformOutput', 0 );
end

