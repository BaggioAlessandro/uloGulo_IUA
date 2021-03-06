package Test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import junit.framework.Test;
import PreProcessing.Parser_submission;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import weka.core.Instances;
import librerie_Aggiunte.Roba_utile;

public class Valutazione {

	static String submission_path = new String("DataMisc/Test/Test_data/res.csv");
	static String output = new String("DataMisc/Test/Test_data/Temp/temp.arff");
	static boolean is_arff = false;
	public static void main(String[] args) throws Exception {
		Instances submission;
		if(!is_arff){
			parser_submission();
			submission = Roba_utile.load(output);
		}else{
			submission = Roba_utile.load(submission_path);
		}
		Instances rating_test = Roba_utile.load("DataMisc/Test/Test_data/relevant_test.arff");
				
		int[] stat = new int[5];
		int[] missing_rec = new int[5];
		int[] n_rec = new int[5];
		float media = 0;
		int count_non_relevant = 0;
		for(int i = 0; i < submission.numInstances(); i++){
			double tot = 0;
			float count = 0;
			Integer[] ok = new Integer[5];
			
			for(int j = 4; j >= 0; j--){
				ok[j] = 0;
			}
			
			//calculete how many rec are missing
			if(submission.instance(i).value(1) == 4000){
				missing_rec[0]++;
			}
			if(submission.instance(i).value(2) == 4000){
				missing_rec[1]++;					
			}
			if(submission.instance(i).value(3) == 4000){
				missing_rec[2]++;
			}
			if(submission.instance(i).value(4) == 4000){
				missing_rec[3]++;
			}
			if(submission.instance(i).value(5) == 4000){
				missing_rec[4]++;
			}
			
			//calculate correct reccomendation and save statistics
			int nRelevant = 0;
			for(int j = 0; j < rating_test.numInstances(); j++){
				if(rating_test.instance(j).value(0) == submission.instance(i).value(0)){
					
					if(rating_test.instance(j).value(1) == submission.instance(i).value(1)){
						count++;
						ok[0] = 1;
						stat[0] ++;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(2)){
						count++;
						ok[1] = 1;
						stat[1] ++;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(3)){
						count++;
						ok[2] = 1;
						stat[2] ++;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(4)){
						count++;
						ok[3] = 1;
						stat[3] ++;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(5)){
						count++;
						ok[4] = 1;
						stat[4] ++;
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
			if(nRelevant!=0){
				media += tot/5;
			}else
				count_non_relevant++;
		}
		
		media = media/submission.numInstances();
		System.out.println(media);
		System.out.println("utenti senza relevant item sono: "+count_non_relevant );
		
		System.out.println("numero di rec effettuate");
		for(int i = 0; i < 5; i++){
			n_rec[i] = submission.numInstances() - missing_rec[i];
			System.out.println(n_rec[i]);
		}
		
		System.out.println("numero di item correttamente raccomandati");
		for(int i = 0; i < 5; i++){
			System.out.println(stat[i]*1.0 / n_rec[i]*1.0);
		}
		
		System.out.println("numero totale di test user e': " + submission.numInstances());
	}
	
	private static void parser_submission() throws IOException{
		CSVReader reader = new CSVReader(new FileReader(submission_path ));
		
		BufferedWriter br = new BufferedWriter(new FileWriter(output));
			
		String new_header = new String("@relation rel\n"+
										"\n"+
										"@attribute UserId numeric\n"+
										"@attribute RecommendedMovieIds1 numeric\n"+
										"@attribute RecommendedMovieIds2 numeric\n"+
										"@attribute RecommendedMovieIds3 numeric\n"+
										"@attribute RecommendedMovieIds4 numeric\n"+
										"@attribute RecommendedMovieIds5 numeric\n"+
										"\n"+
										"@data"+
										"\n");		
		
		StringBuilder sb = new StringBuilder();
		sb.append(new_header);
		
		reader.readNext();
		String[] line = reader.readNext();
		while(line != null){
			sb.append(line[0]);
			sb.append(",");
			String recommendation = line[1].replace(' ', ',');
			if (recommendation.lastIndexOf(',') == recommendation.length()-1)
				recommendation = recommendation.substring(0, recommendation.length()-2);
			sb.append(recommendation);
			sb.append("\n");
			
			line = reader.readNext();
		}
		
		br.write(sb.toString());
		br.close();
		reader.close();
	}
}
