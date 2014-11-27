package Con_Lenskit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.grouplens.lenskit.mf.funksvd.FeatureCount;
import org.grouplens.lenskit.mf.funksvd.FunkSVDItemScorer;
import org.grouplens.lenskit.scored.ScoredId;

import au.com.bytecode.opencsv.CSVWriter;
import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class Minimization {
	
	private static String delimiter = ",";
    private static File inputFile = new File("src/Dati/Test/train.csv");
    private static List<Long> users;
    private static String test_path = new String("src/Dati/Test/test.arff");
    private static String output_path = new String("src/Dati/Test/sub.csv");
    private static String relevant_rating = new String("src/Dati/Test/relevant_test.arff");
    private static Instances rating_test;
    static double min_damping;
    static int feature;
    static double reg_term;
    static EventDAO dao;
    
    public static void main(String[] args) throws Exception {
	    int N_iteration = 1;
	    double reg_term_start = 0.001;
	    int feature_start = 50;
	    double damping_start = 50;
	    
	    Instances test_set = Roba_utile.load(test_path);
        users = new ArrayList<Long>(test_set.numInstances());
        for (int i = 0; i < test_set.numInstances(); i++) {
            users.add((long)test_set.instance(i).value(0));
        }
        
        rating_test = Roba_utile.load(relevant_rating);

        dao = SimpleFileRatingDAO.create(inputFile, delimiter);
        
        min_damping = damping_start;
        feature = feature_start;
        reg_term = reg_term_start;
        
        double map_max = 0;
        
	    for(int i = 0; i < N_iteration; i++){
	    	List<Double> ret = new ArrayList<>(2);
	    	double map_temp = 0;
	    	
	    	switch(2){
	    		case 0: map_temp = minimizeDamping(min_damping, feature, reg_term);
	    				
	    				break;
	    		case 1:
	    				map_temp = minimizeFeature( min_damping, feature, reg_term);
    					break;
	    		case 2:
	    				ret = minimizeRegularization();
	    				if(ret.get(0) > map_max){
	    					map_max = ret.get(0);
	    					reg_term = ret.get(1);
	    				}
    					break;
	    	}
	    }
	    
	    System.out.println("map:"+map_max);
	    System.out.println("regTerm:"+reg_term);
	   
	    
	}

	private static List<Double> minimizeRegularization() throws Exception {
		int n_inner_loop = 1;
		double local_max = 0;
		double local_value = 0;
		double temp_reg_term = reg_term;
		for(int i = 0; i < n_inner_loop; i++){
			LenskitConfiguration config = configure(min_damping, feature, temp_reg_term);
	        //config.set(RegularizationTerm.class).to(temp_reg_term);
	        List<List<Long>> temp = calc_svd(config);
			double valutazione = valutazione(temp);
			temp_reg_term = temp_reg_term + 0.001;
			if(valutazione > local_max){
				local_max = valutazione;
				local_value = temp_reg_term;
			}
		}
		List<Double> ret = new ArrayList<>(2);
		ret.add(local_max);
		ret.add(local_value);
		return ret;
	}

	private static LenskitConfiguration configure(double damping, int fea, double reg){
		LenskitConfiguration config = new LenskitConfiguration();
		config.addComponent(dao);
        //configure svd
        config.bind(ItemScorer.class).to(FunkSVDItemScorer.class);
        config.bind(BaselineScorer.class, ItemScorer.class).to(UserMeanItemScorer.class);
        config.bind(UserMeanBaseline.class, ItemScorer.class).to(ItemMeanRatingItemScorer.class);
        
        //initial parameter
        config.set(MeanDamping.class).to(damping);
        config.set(FeatureCount.class).to(fea);
        config.set(IterationCount.class).to(150);
        config.set(RegularizationTerm.class).to(reg);
        return config;
	}

	private static double minimizeFeature(double min_damping, double feature, double reg_term) {
		int n_inner_loop = 10;
		double local_max = 0;
		for(int i = 0; i < n_inner_loop; i++){
			
		}
		return local_max;
	}

	private static double minimizeDamping(double min_damping, double feature, double reg_term) {
		int n_inner_loop = 10;
		double local_max = 0;
		for(int i = 0; i < n_inner_loop; i++){
			
		}
		return local_max;
		
	}
	    
	private static List<List<Long>> calc_svd(LenskitConfiguration config) {
		Recommender rec = null;
        try {
            rec = LenskitRecommender.build(config);
        } catch (RecommenderBuildException e) {
            throw new RuntimeException("recommender build failed", e);
        }
        
        ItemRecommender irec = rec.getItemRecommender();
        assert irec != null;
    
        CSVWriter writer = null;
        try {
			writer = new CSVWriter(new FileWriter(output_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        List<List<Long>> recomendations = new ArrayList<>(1000);
        for (long user: users) {
            // get 5 recommendation for the user
        	
            List<ScoredId> recs =  irec.recommend(user, 5);
            List<Long> temp = new ArrayList<>(6);
            temp.add(user);
            for(int i = 0; i < 5; i++){
            	temp.add(recs.get(i).getId() );
            }
            recomendations.add(temp);
        }
        
		return recomendations;
        
	}
	
	public static double valutazione(List<List<Long>> submission) throws Exception {
		
				
		float media = 0;
		
		for(int i = 0; i < submission.size(); i++){
			double tot = 0;
			float count = 0;
			Integer[] ok = new Integer[5];
			for(int j = 4; j >= 0; j--){
				ok[j] = 0;
			}
			
			int nRelevant = 0;
			
			for(int j = 0; j < rating_test.numInstances(); j++){
				if(rating_test.instance(j).value(0) == submission.get(i).get(0)){
					if(rating_test.instance(j).value(1) == submission.get(i).get(1)){
						count++;
						ok[0] = 1;
					}else if(rating_test.instance(j).value(1) == submission.get(i).get(2)){
						count++;
						ok[1] = 1;
					}else if(rating_test.instance(j).value(1) == submission.get(i).get(3)){
						count++;
						ok[2] = 1;
					}else if(rating_test.instance(j).value(1) == submission.get(i).get(4)){
						count++;
						ok[3] = 1;
					}else if(rating_test.instance(j).value(1) == submission.get(i).get(5)){
						count++;
						ok[4] = 1;
					}
					nRelevant++;
				}
			}
			
			for(int j = 4; j >= 0; j--){
				if(ok[j]!=0){
					tot = tot + (count/(j+1));
					count--;
				}
			}
			if(nRelevant!=0)
				media += tot/nRelevant;	
		}
		media = media/submission.size();	
		
		return media;
	}
}