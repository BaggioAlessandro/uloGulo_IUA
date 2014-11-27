package Classificatore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class GenereSplit {
	public static String path = new String("src/Dati/Test/");
	
	public static void main(String[] args) throws Exception {
		int topN = 25;
		String training = new String(path+"join-user-rating.arff");
		Instances data = Roba_utile.load(training);
		/*
		Instances[] split = Most_popular.genere_split(data);
		ArrayList<Frequency[]> popular = new ArrayList<Frequency[]>();
		
		for(int i = 0; i < split.length; i++){
			popular.add(Most_popular.calc_frequency_relevant(split[i]));
		}
		*/
		Frequency[] popular = Most_popular.calc_frequency(data);
		
		write2(popular, topN);
		System.out.println("fine");
	
	}
	
	public static void write(ArrayList<Frequency[]> popular, int num) throws IOException{
		BufferedWriter  writer;

		for(int i = 0; i < popular.size(); i++){
		    writer = new BufferedWriter(new FileWriter("src/Dati/Temp/pref"+i+".arff"));
		    String output = new String("@relation rel\n@attribute movieId INTEGER\n@attribute occ INTEGER\n\n@data\n");
		    for(int j = popular.get(i).length-1; j > popular.get(i).length-(num+1); j--){
		    	output += popular.get(i)[j].toString();
		    	output += "\n";
		    	
		    }
		    writer.write(output);
		    writer.close();
		}
	}
	
	
	public static void write2(Frequency[] popular, int num) throws IOException{
		BufferedWriter  writer;

	    writer = new BufferedWriter(new FileWriter("src/Dati/Temp/pref"+0+".arff"));
	    String output = new String("@relation rel\n@attribute movieId INTEGER\n@attribute occ INTEGER\n\n@data\n");
	    for(int j = popular.length-1; j > popular.length-(num+1); j--){
	    	output += popular[j].toString();
	    	output += "\n";
	    }
		
	    writer.write(output);
		writer.close();
	}
	/*
	Instances[] split = Most_popular.age_split(data);
	Instances[] split2 = new Instances[split.length * 2];
	for(int i = 0; i < split.length; i++){
		//make the second split for each category
		Instances[] temp = Most_popular.genere_split(split[i]);
		
		//copy in split2
		for(int j = 0; j < temp.length; j++){
			split2[i*2+j] = temp[j];
		}
	}
	ArrayList<Frequency[]> popular = new ArrayList<Frequency[]>();
	
	for(int i = 0; i < split2.length; i++){
		popular.add(Most_popular.calc_frequency(split2[i]));
	}
	*/

}
