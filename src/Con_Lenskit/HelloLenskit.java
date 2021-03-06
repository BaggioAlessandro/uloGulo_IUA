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
	
	private String delimiter = ",";
    private File inputFile = new File("DataMisc/train_NH.csv");
    private List<Long> users;
    private String test_path = new String("DataMisc/test.arff");
    private String output_path = new String("DataMisc/res.csv");
    
    
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

    public HelloLenskit(String[] args) throws Exception {
    	Instances test_set = Roba_utile.load(test_path);
        users = new ArrayList<Long>(test_set.numInstances());
        for (int i = 0; i < test_set.numInstances(); i++) {
            users.add((long)test_set.instance(i).value(0));
        }
    }

    public void run() {
        // We first need to configure the data access.
    	EventDAO dao = SimpleFileRatingDAO.create(inputFile, delimiter);
        // Second step is to create the LensKit configuration...
        LenskitConfiguration config = new LenskitConfiguration();
        config.addComponent(dao);
        
        //configure the algorithm
        config.bind(ItemScorer.class)
              .to(ItemItemScorer.class);
        
        config.bind(BaselineScorer.class,ItemScorer.class).to(ItemMeanRatingItemScorer.class);
        
        config.bind(UserVectorNormalizer.class).to(BaselineSubtractingUserVectorNormalizer.class);

        config.set(NeighborhoodSize.class).to(200);

        config.within(VectorSimilarity.class).set(SimilarityDamping.class).to(50);   //shrinkage factor

        //start the computation
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
            List<ScoredId> recs = irec.recommend(user);
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