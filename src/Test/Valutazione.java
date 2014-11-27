package Test;

import weka.core.Instances;
import librerie_Aggiunte.Roba_utile;

public class Valutazione {

	public static void main(String[] args) throws Exception {
		Instances rating_test = Roba_utile.load("src/Dati/Test/relevant_test.arff");
		
		Instances submission = Roba_utile.load("src/Dati/Test/submission.arff");
		
		float media = 0;
		
		for(int i = 0; i < submission.numInstances(); i++){
			double tot = 0;
			float count = 0;
			Integer[] ok = new Integer[5];
			
			for(int j = 4; j >= 0; j--){
				ok[j] = 0;
			}
			
			int nRelevant = 0;
			
			for(int j = 0; j < rating_test.numInstances(); j++){
				if(rating_test.instance(j).value(0) == submission.instance(i).value(0)){
					if(rating_test.instance(j).value(1) == submission.instance(i).value(1)){
						count++;
						ok[0] = 1;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(2)){
						count++;
						ok[1] = 1;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(3)){
						count++;
						ok[2] = 1;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(4)){
						count++;
						ok[3] = 1;
					}else if(rating_test.instance(j).value(1) == submission.instance(i).value(5)){
						count++;
						ok[4] = 1;
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
			if(nRelevant!=0)
				media += tot/5;
			else
				System.out.println("fdsfsd");
			
		}
		
		media = media/submission.numInstances();
		System.out.println(media);
		System.out.println("Top popular scored: 0.3143" );
		
	}
}
