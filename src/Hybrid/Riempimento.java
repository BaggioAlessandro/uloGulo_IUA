package Hybrid;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import librerie_Aggiunte.Roba_utile;
import weka.core.FastVector;
import weka.core.Instances;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Riempimento {
	
	private static String ar_path = new String("DataMisc/Hybrid/ar.csv");
	private static String other_path = new String("DataMisc/Hybrid/res.csv");
	private static String output_path = new String("DataMisc/Test/Test_data/res.csv");
	private static int no_submission = 4000;
	
	public static void main(String[] args) throws Exception {
		String output_ar = new String("DataMisc/Hybrid/Temp/temp1.arff");	
		String output_other = new String("DataMisc/Hybrid/Temp/temp2.arff");
		
		//transform and load submissions
		parser_submission(ar_path, output_ar);
		parser_submission(other_path, output_other);
		Instances ar_sub = Roba_utile.load(output_ar);
		Instances other_sub = Roba_utile.load(output_other);
		
		//create final submission struct
		FastVector fv = new FastVector(ar_sub.numAttributes());
		for(int i = 0; i < ar_sub.numAttributes(); i++){
			fv.addElement(ar_sub.attribute(i));
		}
		
		Instances final_sub = new Instances("rel", fv, 1500);
		
		for(int i = 0; i < ar_sub.numInstances(); i++){
			final_sub.add(ar_sub.instance(i));
			List<Integer> rec = new ArrayList<Integer>(5);
			for(int j = 1; j < 6; j++){
				if((int)ar_sub.instance(i).value(j)!= no_submission){
					rec.add((int)ar_sub.instance(i).value(j));
				}else{
					//start filling with other submission
					for(int k = 1; j < 6; k++){
						if(! (rec.contains((int)other_sub.instance(i).value(k)))){
							rec.add((int)other_sub.instance(i).value(k));
							final_sub.instance(i).setValue(j, other_sub.instance(i).value(k));
							j++;
						}
					}
					break;	//go to next test user
				}
			}
		}
		
		Roba_utile.save(final_sub, output_path);
		System.out.println("Fine");
	}
	
	private static void parser_submission(String submission_path, String output) throws IOException{
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
