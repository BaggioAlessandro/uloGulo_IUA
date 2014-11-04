package Prediction;

import Classificatore.Genere;
import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class Age_Genere_based {

	public static void main(String[] args) throws Exception {
		String user_path = new String("src/Dati/test_user.arff");
		String training = new String("src/Dati/super-joinsenzaGenere2.arff");
		String sample_path = new String("src/Dati/Submission/sampleSubmission.arff");
		
		String[] popular = new String[12];
		Instances[] popular_data = new Instances[12];
		
		Instances user_data = Roba_utile.load(user_path);
		Instances data = Roba_utile.load(training);
		Instances sample_data = Roba_utile.load(sample_path);
		
		for(int i = 0; i < 12; i++){
			popular[i] = new String("src/Dati/Temp/pref" + i + ".arff");
			popular_data[i] = Roba_utile.load(popular[i]);
		}
		
		for(int i = 0; i < user_data.numInstances(); i++){
			switch((int)user_data.instance(i).value(2)){
				case 18:
					if(user_data.instance(i).stringValue(1).equalsIgnoreCase("F")){
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[0 + Genere.F.value].instance(j).value(0));
								k++;
							}
						}
					}else{
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[0 + Genere.M.value].instance(j).value(0));
								k++;
							}
						}
					}
					break;
				case 25:
					if(user_data.instance(i).stringValue(1).equalsIgnoreCase("F")){
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[2 + Genere.F.value].instance(j).value(0));
								k++;
							}
						}
					}else{
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[2 + Genere.M.value].instance(j).value(0));
								k++;
							}
						}
					}
					break;
				case 35:
					if(user_data.instance(i).stringValue(1).equalsIgnoreCase("F")){
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[4 + Genere.F.value].instance(j).value(0));
								k++;
							}
						}
					}else{
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[4 + Genere.M.value].instance(j).value(0));
								k++;
							}
						}
					}
					break;
				case 45:
					if(user_data.instance(i).stringValue(1).equalsIgnoreCase("F")){
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[6 + Genere.F.value].instance(j).value(0));
								k++;
							}
						}
					}else{
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[6 + Genere.M.value].instance(j).value(0));
								k++;
							}
						}
					}
					break;
				case 50:
					if(user_data.instance(i).stringValue(1).equalsIgnoreCase("F")){
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[8 + Genere.F.value].instance(j).value(0));
								k++;
							}
						}
					}else{
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[8 + Genere.M.value].instance(j).value(0));
								k++;
							}
						}
					}
					break;
				case 56:
					if(user_data.instance(i).stringValue(1).equalsIgnoreCase("F")){
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[10 + Genere.F.value].instance(j).value(0));
								k++;
							}
						}
					}else{
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[10 + Genere.M.value].instance(j).value(0));
								k++;
							}
						}
					}
					break;
					
				//se non c'è un match assegna all'età 35
				default:
					if(user_data.instance(i).stringValue(1).equalsIgnoreCase("F")){
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[4 + Genere.F.value].instance(j).value(0));
								k++;
							}
						}
					}else{
						for(int k = 0, j = 0; k < 5; j++){
							if(!Roba_utile.visto((int)user_data.instance(i).value(0), (int)popular_data[0].instance(j).value(0), data)){
								sample_data.instance(i).setValue(k+1, popular_data[4 + Genere.M.value].instance(j).value(0));
								k++;
							}
						}
					}
					break;
			}
			
			
		}
		Roba_utile.save(sample_data, sample_path);
		System.out.println("fine");

	}


}
