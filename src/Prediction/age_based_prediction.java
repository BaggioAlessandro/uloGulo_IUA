package Prediction;

import PreProcessing.Parser_submission;
import weka.core.Instances;
import librerie_Aggiunte.Roba_utile;


//this prediction require the top 10  popular film in src/Dati/preferitiv.2-" + i + ".arff
public class age_based_prediction {

	public static void main(String[] args) throws Exception {
		String user_path = new String("src/Dati/test_user.arff");
		String training = new String("src/Dati/super-joinsenzaGenere2.arff");
		String sample_path = new String("src/Dati/Submission/sampleSubmission.arff");
		
		String[] popular = new String[6];
		Instances[] popular_data = new Instances[6];
		
		Instances user_data = Roba_utile.load(user_path);
		Instances data = Roba_utile.load(training);
		Instances sample_data = Roba_utile.load(sample_path);
		
		for(int i = 0; i < 6; i++){
			popular[i] = new String("src/Dati/Temp/pref" + i + ".arff");
			popular_data[i] = Roba_utile.load(popular[i]);
		}
		
		for(int i = 0; i < user_data.numInstances(); i++){
			switch((int)user_data.instance(i).value(2)){
				case 18:
					for(int k = 0, j = 0; k < 5; j++){
						if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
							sample_data.instance(i).setValue(k+1, popular_data[0].instance(j).value(0));
							k++;
						}
					}
					break;
				case 25:
					for(int k = 0, j = 0; k < 5; j++){
						if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[1].instance(j).value(0), data)){
							sample_data.instance(i).setValue(k+1, popular_data[1].instance(j).value(0));
							k++;
						}
					}
					break;
				case 35:
					for(int k = 0, j = 0; k < 5; j++){
						if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[2].instance(j).value(0), data)){
							sample_data.instance(i).setValue(k+1, popular_data[2].instance(j).value(0));
							k++;
						}
					}
					break;
				case 45:
					for(int k = 0, j = 0; k < 5; j++){
						if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[3].instance(j).value(0), data)){
							sample_data.instance(i).setValue(k+1, popular_data[3].instance(j).value(0));
							k++;
						}
					}
					break;
				case 50:
					for(int k = 0, j = 0; k < 5; j++){
						if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[4].instance(j).value(0), data)){
							sample_data.instance(i).setValue(k+1, popular_data[4].instance(j).value(0));
							k++;
						}
					}
					break;
				case 56:
					for(int k = 0, j = 0; k < 5; j++){
						if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[5].instance(j).value(0), data)){
							sample_data.instance(i).setValue(k+1, popular_data[5].instance(j).value(0));
							k++;
						}
					}
					break;
				default:
					for(int k = 0, j = 0; k < 5; j++){
						if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[2].instance(j).value(0), data)){
							sample_data.instance(i).setValue(k+1, popular_data[2].instance(j).value(0));
							k++;
						}
					}
					break;
			}
			
			
		}
		Roba_utile.save(sample_data, sample_path);
		System.out.println("fine");

	}

}
