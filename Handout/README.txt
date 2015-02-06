FOR MATLAB:
    - User KNN algorithm:
        It was a modification over the code provided by Massimo Quadrana. For convenience we pack it all under ./MATLAB/UserKNN folder
        1) Run script "create_submission.m" from ./MATLAB/UserKNN folder
        2) Result is available as "res.csv". It should be quite close to the our best performing User KNN altough some last minute parameter tuning might render it different.
        3) We used "test_submission.m" script for cross-validation.

    - Hybrid final submission
        1) Run script "create_combined_submissions.m" from ./MATLAB/Hybrid folder
        2) Results are combined from submissions available under that folder and produce our final "submission.csv"

FOR Java:
    - In the Hybrid subfolder you can find the code to add Association Rules to a recommendation.
    - In order to run the file you should change in the code the correct path of the recommendation to enhance, and the recommendation done with AR. Note that when producing the AR you should specified an integer for missing recommendation (that are going to be filled with the recommendation that should be enhanced), in our case this special number was "10000".

FOR R (Association rules mining):
    This are the steps we followed on Windows
    1) Install R-Studio, an IDE for R
    2) Run script "recommenderlab-submission.R" which trains a recommender using association rules and generates a submission at "./submissions/submission.csv"
    3) Install required packages including plyr, Metrics, recommenderlab.
    4) As some packages are being modified, further error fix is required. If interested, please contact us.