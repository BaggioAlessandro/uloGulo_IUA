package Test;

import java.util.ArrayList;
import java.util.List;

import weka.core.Instances;
import librerie_Aggiunte.Roba_utile;

public class Filtering {

	public static void main(String[] args) throws Exception {
		int numUser = 7000;
		Instances data = Roba_utile.load("src/Dati/train.arff");
		List<Integer> eliminate = new ArrayList<Integer>();
		int[] count = new int[numUser];
		for(int i = 0; i < data.numInstances(); i++){
			count[(int)data.instance(i).value(0)] ++;
		}
		
		for(int i = 0; i < numUser; i++){
			if(count[i] == 5){
				eliminate.add(i);
			}
		}
		
		for(int i = 0; i < data.numInstances();){
			if(eliminate.contains((int) data.instance(i).value(0))){
				data.delete(i);
			}
			else
				i++;
		}
		
		Roba_utile.save(data, "src/Dati/Test/train_all.arff");
	}

}
