package Prediction;

import Classificatore.Genere;
import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class genere_based_prediction {
	
	public static String path = new String("src/Dati/Test/");
	public static String output = new String("new_sample.arff");
	
public static void main(String[] args) throws Exception {
	int split = 2;
	String user_path = new String(path+"test_user_data.arff");
	String training = new String(path+"join-user-rating.arff");
	String sample_path = new String(path+output);
	
	String[] popular = new String[split];
	Instances[] popular_data = new Instances[split];
	
	Instances user_data = Roba_utile.load(user_path);
	Instances data = Roba_utile.load(training);
	Instances sample_data = Roba_utile.load(sample_path);
	
	for(int i = 0; i < split; i++){
		popular[i] = new String("src/Dati/Temp/pref" + i + ".arff");
		popular_data[i] = Roba_utile.load(popular[i]);
	}
	
	for(int i = 0; i < user_data.numInstances(); i++){
		if(user_data.instance(i).stringValue(1).equalsIgnoreCase("M")){
			for(int k = 0, j = 0; k < 5; j++){
				if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[Genere.M.value].instance(j).value(0), data)){
					sample_data.instance(i).setValue(k+1, popular_data[Genere.M.value].instance(j).value(0));
					k++;
				}
			}
		}
		else{
			for(int k = 0, j = 0; k < 5; j++){
				if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[Genere.F.value].instance(j).value(0), data)){
					sample_data.instance(i).setValue(k+1, popular_data[Genere.F.value].instance(j).value(0));
					k++;
				}
			}
		}
		System.out.println(i);
			
	}
	Roba_utile.save(sample_data, sample_path);
	System.out.println("fine");

}
}
