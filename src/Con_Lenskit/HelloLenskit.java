package Con_Lenskit;

import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.Recommender;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.data.dao.SimpleFileRatingDAO;
import org.grouplens.lenskit.knn.NeighborhoodSize;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.mf.svd.BiasedMFItemScorer;
import org.grouplens.lenskit.scored.ScoredId;
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;
import org.grouplens.lenskit.vectors.similarity.PearsonCorrelation;
import org.grouplens.lenskit.vectors.similarity.SimilarityDamping;
import org.grouplens.lenskit.vectors.similarity.VectorSimilarity;

import au.com.bytecode.opencsv.CSVWriter;
import weka.core.Instances;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import librerie_Aggiunte.Roba_utile;

/**
 * Demonstration app for LensKit. This application builds an item-item CF model
 * from a CSV file, then generates recommendations for a user.
 *
 * Usage: java org.grouplens.lenskit.hello.HelloLenskit ratings.csv user
 */
public class HelloLenskit implements Runnable {
    public static void main(String[] args) throws Exception {
        HelloLenskit hello = new HelloLenskit(args);
        try {
            hello.run();
        } catch (RuntimeException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private String delimiter = ",";
    private File inputFile = new File("DataMisc/train_NH.csv");
    private List<Long> users;
    private String test_path = new String("DataMisc/test.arff");
    private String output_path = new String("DataMisc/res.csv");

    public HelloLenskit(String[] args) throws Exception {
    	Instances test_set = Roba_utile.load(test_path);
        users = new ArrayList<Long>(test_set.numInstances());
        for (int i = 0; i < test_set.numInstances(); i++) {
            users.add((long)test_set.instance(i).value(0));
        }
    }

    public void run() {
        // We first need to configure the data access.
        // We will use a simple delimited file; you can use something else like
        // a database (see JDBCRatingDAO).
    	EventDAO dao = SimpleFileRatingDAO.create(inputFile, delimiter);

        // Second step is to create the LensKit configuration...
        LenskitConfiguration config = new LenskitConfiguration();
        // ... configure the data source
        config.addComponent(dao);
        // ... and configure the item scorer.  The bind and set methods
        // are what you use to do that. Here, we want an item-item scorer.
        config.bind(ItemScorer.class)
              .to(BiasedMFItemScorer.class);

        config.bind(VectorSimilarity.class)
                .to(PearsonCorrelation.class);

        config.set(NeighborhoodSize.class).to(20);

        config.set(SimilarityDamping.class).to(10);   //shrinkage factor
        
        // let's use personalized mean rating as the baseline/fallback predictor.
        // 2-step process:
        // First, use the user mean rating as the baseline scorer
        config.bind(BaselineScorer.class, ItemScorer.class)
               .to(UserMeanItemScorer.class);
        // Second, use the item mean rating as the base for user means
        config.bind(UserMeanBaseline.class, ItemScorer.class)
              .to(ItemMeanRatingItemScorer.class);
        // and normalize ratings by baseline prior to computing similarities
        config.bind(UserVectorNormalizer.class)
              .to(BaselineSubtractingUserVectorNormalizer.class);

        // There are more parameters, roles, and components that can be set. See the
        // JavaDoc for each recommender algorithm for more information.

        // Now that we have a factory, build a recommender from the configuration
        // and data source. This will compute the similarity matrix and return a recommender
        // that uses it.
        Recommender rec = null;
        try {
            rec = LenskitRecommender.build(config);
        } catch (RecommenderBuildException e) {
            throw new RuntimeException("recommender build failed", e);
        }

        // we want to recommend items
        ItemRecommender irec = rec.getItemRecommender();
        assert irec != null; // not null because we configured one
        // for users
        CSVWriter writer = null;
        try {
			writer = new CSVWriter(new FileWriter(output_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        String[] output = new String[2];
        output[0] = new String("UserId");
        output[1] = new String("RecommendedMovieIds");
        writer.writeNext(output);
        for (long user: users) {
            // get 5 recommendation for the user
            List<ScoredId> recs = irec.recommend(user, 5);
            System.out.println(user);
            output[0] = new String(""+user);
            output[1] = new String("");
            for (ScoredId item: recs) {
                output[1] += item.getId() + " ";
            }
            writer.writeNext(output);
        }
        try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("fine");        
    }
}