package Prediction;

import Classificatore.Genere;
import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class genere_based_prediction {
	
public static void main(String[] args) throws Exception {
	int split = 2;
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
	}
	
	for(int i = 0; i < user_data.numInstances(); i++){
		if(user_data.instance(i).stringValue(1).equalsIgnoreCase("M")){
			for(int k = 0, j = 0; k < 5; j++){
				if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
					sample_data.instance(i).setValue(k+1, popular_data[Genere.M.value].instance(j).value(0));
					k++;
				}
			}
		}
		else{
			for(int k = 0, j = 0; k < 5; j++){
				if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
					sample_data.instance(i).setValue(k+1, popular_data[Genere.F.value].instance(j).value(0));
					k++;
				}
			}
		}
			
	}
	Roba_utile.save(sample_data, sample_path);
	System.out.println("fine");

}
}
