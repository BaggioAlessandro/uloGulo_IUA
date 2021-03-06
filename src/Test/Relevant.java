package Test;

import librerie_Aggiunte.Roba_utile;
import weka.core.Instances;

public class Relevant {

	public static void main(String[] args) throws Exception {
		Instances rating_test = Roba_utile.load("DataMisc/Test/Test_data/rating_test.arff");
		
		
		for(int i = 0; i < rating_test.numInstances();){
			if(rating_test.instance(i).value(2) < 4){
				rating_test.delete(i);
			}
			else i++;
		}
		
		Roba_utile.save(rating_test, "DataMisc/Test/Test_data/relevant_test.arff");
		
		System.out.println("fine");
		
	}
}
