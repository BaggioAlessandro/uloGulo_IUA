package PreProcessing;

import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class Genere {
	
	public static void main(String[] args) throws Exception{
		String training = new String("src/Dati/super-join.arff");
		Instances data = Roba_utile.load(training);
		
		for(int i = 0; i < data.numInstances(); i++){
			String string = data.instance(i).stringValue(2);
			String[] strings = string.split("[\\x7C]");
			for(int j = 0; j < strings.length; j++){
				if(!Generi_base.in(strings[j])){
					System.out.println(strings[j]);
				}
			}
			
		}
	}
}
