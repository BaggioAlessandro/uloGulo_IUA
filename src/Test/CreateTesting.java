package Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.datagenerators.Test;
import librerie_Aggiunte.Roba_utile;

public class CreateTesting {

	public static void main(String[] args) throws Exception {
		int numUser = 7000;
		Instances data = Roba_utile.load("src/Dati/Test/train.arff");
		Instances submission_sample = Roba_utile.load("src/Dati/Test/sampleSubmission.arff");
		
		FastVector fv = new FastVector(data.numAttributes());
		for(int i = 0; i < data.numAttributes(); i++){
			fv.addElement(data.attribute(i));
		}
		
		Instances rating_test = new Instances("rel", fv, 5000);
		Instances user_data = new Instances("rel", fv, 5000);
		user_data.deleteAttributeAt(2);
		user_data.deleteAttributeAt(1);
		
		submission_sample.delete();

		Set<Integer> user = new HashSet<Integer>();
		int[] count = new int[numUser];
		for(int i = 0; i < data.numInstances(); i++){
			user.add( (int)data.instance(i).value(0));
			count[(int)data.instance(i).value(0)] ++;
		}
		
		List<Integer> user_list = new ArrayList<>(user);
		Collections.shuffle(user_list);
		
		List<Integer> test_user = new ArrayList<Integer>();
		for(int i = 0, k = 0; k < (5000/100)*15; k++){
			
			test_user.add(user_list.get(i));
			user_list.remove(i);
		}
		
		
		for(int i = 0; i < test_user.size(); i++){
			List<Integer> row = new ArrayList<Integer>();
			int k = rating_test.numInstances();		//last value di rating_test
			for(int j = 0; j < data.numInstances();){
				if((int)data.instance(j).value(0) == test_user.get(i) ){
					rating_test.add(data.instance(j));
					data.delete(j);
					
					row.add(k);
					k++;
				}
				else
					j++;
			}
						
			Collections.shuffle(row);
			
			List<Integer> top = new ArrayList<Integer>(5);
			for(int j = 0; j < 5; j++){
				top.add(row.get(j));
				data.add(rating_test.instance(top.get(j)));
			}
			
			Collections.sort(top);
			
			for(int j = 4; j >= 0; j--){
				rating_test.delete(top.get(j));
			}
			//prepare test arff
			Instance newUser = new Instance(1);
			newUser.setValue(0, test_user.get(i));
			user_data.add(newUser);
			
			//prepare sample arff
			Instance new_sample = new Instance(6);
			new_sample.setValue(0, test_user.get(i));
			new_sample.setValue(1, 1);
			new_sample.setValue(2, 2);
			new_sample.setValue(3, 3);
			new_sample.setValue(4, 4);
			new_sample.setValue(5, 5);
			submission_sample.add(new_sample);
		}
		
		
		System.out.println("fine");
		
		Roba_utile.save(submission_sample, "src/Dati/Test/new_submission_sample.arff");
		Roba_utile.save(user_data, "src/Dati/Test/test.arff");
		Roba_utile.save(rating_test, "src/Dati/Test/rating_test.arff");
		Roba_utile.save(data, "src/Dati/Test/train.arff");
	}

}