package Prediction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;
import Classificatore.Genere;
import Con_Lenskit.PredittoreLenskit;

public class prediction_con_lenskit {

	public static void main(String[] args) throws Exception {
		int split = 2;
		int numRec = 5;
		int numCandidates = 7; 
		String user_path = new String("src/Dati/test_user.arff");
		String training = new String("src/Dati/super-joinsenzaGenere2.arff");
		String sample_path = new String("src/Dati/Submission/sampleSubmission.arff");
		
		String[] popular = new String[split];
		Instances[] popular_data = new Instances[split];
		
		Instances user_data = Roba_utile.load(user_path);
		Instances data = Roba_utile.load(training);
		Instances sample_data = Roba_utile.load(sample_path);
		
		
		for(int i = 0; i < split; i++){
			popular[i] = new String("src/Dati/Temp/pref" + i + ".arff");
			popular_data[i] = Roba_utile.load(popular[i]);
			System.out.println(popular_data[i].numInstances());
			for(int j = numCandidates; j < popular_data[i].numInstances();){
				popular_data[i].delete(j);
			}
			System.out.println(popular_data[i]);
			
		}
		
		PredittoreLenskit pred = new PredittoreLenskit(new File("src/Dati/train.csv"), new String("src/Dati/test.arff"));
		
		pred.default_option();
		
		List<Set<Long>> candidates = new ArrayList<Set<Long>>();
		
		for(int i = 0; i < split; i++){
			candidates.add(new HashSet<Long>());
			for(int j = 0; j < popular_data[i].numInstances(); j++){
				candidates.get(i).add((long)popular_data[i].instance(j).value(0) );
			}
			
		}
		
		for(int i = 0; i < user_data.numInstances(); i++){
			Set<Long> exclude = new HashSet();
			int[] res = new int[numRec];
			if(user_data.instance(i).stringValue(1).equalsIgnoreCase("M")){
				for(long film_id: candidates.get(Genere.M.value)){
					if(Roba_utile.visto((int)user_data.instance(i).value(0), (int)film_id, data)){
						exclude.add(film_id);
					}
				}
				res = pred.prediction((long)user_data.instance(i).value(0), numRec, candidates.get(Genere.M.value), exclude);
				for(int k = 0; k < res.length; k++){
					sample_data.instance(i).setValue(k+1, res[k]);
				}
			}
			else{
				for(long film_id: candidates.get(Genere.F.value)){
					if(Roba_utile.visto((int)user_data.instance(i).value(0), (int)film_id, data)){
						exclude.add(film_id);
					}
				}
				res = pred.prediction((long)user_data.instance(i).value(0), numRec, candidates.get(Genere.F.value), exclude);
				for(int k = 0; k < res.length; k++){
					sample_data.instance(i).setValue(k+1, res[k]);
				}
			}
			System.out.println(i);	
		}
		Roba_utile.save(sample_data, sample_path);
		System.out.println("fine");

	}

}
