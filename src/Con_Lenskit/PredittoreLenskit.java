package Con_Lenskit;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.Recommender;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.MeanDamping;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.data.dao.SimpleFileRatingDAO;
import org.grouplens.lenskit.iterative.IterationCount;
import org.grouplens.lenskit.iterative.RegularizationTerm;
import org.grouplens.lenskit.knn.NeighborhoodSize;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.mf.funksvd.FeatureCount;
import org.grouplens.lenskit.mf.funksvd.FunkSVDItemScorer;
import org.grouplens.lenskit.scored.ScoredId;
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;
import org.grouplens.lenskit.vectors.similarity.PearsonCorrelation;
import org.grouplens.lenskit.vectors.similarity.SimilarityDamping;
import org.grouplens.lenskit.vectors.similarity.VectorSimilarity;

import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class PredittoreLenskit {
	private String delimiter = ",";
    private File inputFile;
    private List<Long> users;
    public LenskitConfiguration config;
    private ItemRecommender irec;
    
    public PredittoreLenskit(File input_rating, String test_path) throws Exception{
    	//initialization
    	inputFile = input_rating;
    	Instances test_set = Roba_utile.load(test_path);
        users = new ArrayList<Long>(test_set.numInstances());
        for (int i = 0; i < test_set.numInstances(); i++) {
            users.add((long)test_set.instance(i).value(0));
        }
        
        EventDAO dao = SimpleFileRatingDAO.create(inputFile, delimiter);
        config = new LenskitConfiguration();
        config.addComponent(dao);
        
    }
    
    public void default_option(){
    	config.bind(ItemScorer.class)
        .to(ItemItemScorer.class);

        config.bind(VectorSimilarity.class)
          .to(PearsonCorrelation.class);

        config.set(NeighborhoodSize.class).to(15);

        config.set(SimilarityDamping.class).to(10);
        
        // First, use the user mean rating as the baseline scorer
        config.bind(BaselineScorer.class, ItemScorer.class)
               .to(UserMeanItemScorer.class);
        // Second, use the item mean rating as the base for user means
        config.bind(UserMeanBaseline.class, ItemScorer.class)
              .to(ItemMeanRatingItemScorer.class);
        // and normalize ratings by baseline prior to computing similarities
        config.bind(UserVectorNormalizer.class)
              .to(BaselineSubtractingUserVectorNormalizer.class);
        
        Recommender rec = null;
        try {
            rec = LenskitRecommender.build(config);
        } catch (RecommenderBuildException e) {
            throw new RuntimeException("recommender build failed", e);
        }
        
        irec = rec.getItemRecommender();
        assert irec != null;
    }

    public void svd_option(){
    	
    	/*
    	config.within(UserVectorNormalizer.class);
    	{
	         config.bind(BaselineScorer.class, ItemScorer.class).to(ItemMeanRatingItemScorer.class);
	         config.set((Class<? extends Annotation>) MeanDamping.class).to(5.0);
	    }
    	*/
    	
    	config.bind(ItemScorer.class).to(FunkSVDItemScorer.class);
        
        config.bind(BaselineScorer.class, ItemScorer.class).to(UserMeanItemScorer.class);
        config.bind(UserMeanBaseline.class, ItemScorer.class).to(ItemMeanRatingItemScorer.class);
        config.set(MeanDamping.class).to(50.0d);
        config.set(FeatureCount.class).to(50);
        config.set(IterationCount.class).to(150);
        config.set(RegularizationTerm.class).to(0.001);
    	
    	Recommender rec = null;
        try {
            rec = LenskitRecommender.build(config);
        } catch (RecommenderBuildException e) {
            throw new RuntimeException("recommender build failed", e);
        }
        
        irec = rec.getItemRecommender();
        assert irec != null;
    }
    
    public int[] prediction(long user, int n, Set<Long> candidates, Set<Long> exclude){
    	int[] outLong = new int[n];
    	
        List<ScoredId> recs = irec.recommend(user, n, candidates, exclude);
        for(int i = 0; i < outLong.length; i++){
        	outLong[i] = (int)recs.get(i).getId();
        }
		return outLong;
    	
    }
}
