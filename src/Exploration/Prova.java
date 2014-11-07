package Exploration;

import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;
import Classificatore.Most_popular;

public class Prova {
	
	public static void count_genere_Occupation() throws Exception{
		String training = new String("src/Dati/super-join.arff");
		Instances data = Roba_utile.load(training);
		Instances[] split = Most_popular.occupation_split(data);
		System.out.println(split.length);
		
		
	}

}
