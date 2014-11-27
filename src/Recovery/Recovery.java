package Recovery;

import java.util.ArrayList;
import java.util.List;

import weka.core.Instances;
import librerie_Aggiunte.Roba_utile;

public class Recovery {

	public static void main(String[] args) throws Exception {
		int numUser = 6500;
		
		String path = new String("src/Dati/Recover/");
		
		Instances rating_true = Roba_utile.load(path+"ratings_true.arff");
		Instances rating = Roba_utile.load(path+"train.arff");
		
		Instances old_user_data = Roba_utile.load(path+"users_true.arff");
		Instances new_user_data = Roba_utile.load(path+"userMeta.arff");
		
		//create structures
		Map[] mapping = new Map[numUser];
		for(int i = 0; i < numUser; i++){
			mapping[i] = new Map();
		}
		
		Info[] new_info = new Info[numUser];
		Info[] old_info = new Info[numUser];
		
		//create info matrix
		for(int i = 0; i < numUser; i++){
			new_info[i] = new Info(0);
			old_info[i] = new Info(0);
		}
		
		//prepare info matrix
		for(int i = 0; i < new_user_data.numInstances(); i++){
			int user_id = (int) new_user_data.instance(i).value(0);
			new_info[user_id] = new Info(user_id);
		}
		
		for(int i = 0; i < old_user_data.numInstances(); i++){
			int user_id = (int) old_user_data.instance(i).value(0);
			old_info[user_id] = new Info(user_id);
		}
		
		
		//update info matrix
		for(int i = 0; i < rating.numInstances(); i++){
			int user_id = (int) rating.instance(i).value(0);
			new_info[user_id].addRating((int)rating.instance(i).value(2));
		}
		
		for(int i = 0; i < rating_true.numInstances(); i++){
			int user_id = (int) rating_true.instance(i).value(0);
			old_info[user_id].addRating((int)rating_true.instance(i).value(2));
		}
		
		int test_user = 0;
		for(int i = 0; i < new_user_data.numInstances(); i++){
			int user_id = (int) new_user_data.instance(i).value(0);
			if(new_info[user_id].numRating < 20){
				test_user++;
			}
		}
		
		System.out.println(test_user);
		
		int count = 0;
		test_user = 0;
		
		//create not mapped list
		List<Integer> not_mapped = new ArrayList<Integer>(1080);
		
		/*
		Info[] temp = new_info;
		new_info = old_info;
		old_info = temp;
		*/
		//mapping user
		for(int i = 0; i < new_info.length; i++){
			
			if(new_info[i].id != 0){	
				int inner_count = 0;
				int equals = 0;
				for(int j = 0; j < old_info.length; j++){
					if(new_info[i].compareTo(old_info[j]) == 0){
						inner_count++;
						equals = old_info[j].id;
					}
				}
				
				if(inner_count == 1){
					mapping[i].id_new = i;
					mapping[i].id_old = equals;
					count++;
					if(new_info[i].numRating <= 5){
						System.out.println("test_user");
					}
				}else{
					if(new_info[i].numRating > 5){
						
						System.out.println("problem");
						System.out.println(new_info[i].numRating);
						System.out.println(i);
						System.out.println(equals);
						test_user++;
					}
				}
				
				
			}
		}
		
		//filtering not mapped from original dataset
		
		for(int i = 0; i < rating_true.numInstances();){
			if(not_mapped.contains((int) rating_true.instance(i).value(0))){
				rating_true.delete(i);
			}
			else
				i++;
		}
		//mapping film!!!
		//create structures
		/*
				Map[] mapping_film = new Map[numUser];
				for(int i = 0; i < numUser; i++){
					mapping[i] = new Map();
				}
				
				Info[] new_film_info = new Info[numUser];
				Info[] old_film_info = new Info[numUser];
				
				//create info matrix
				for(int i = 0; i < numUser; i++){
					new_film_info[i] = new Info(0);
					old_film_info[i] = new Info(0);
				}
				
				//prepare info matrix
				for(int i = 0; i < new_user_data.numInstances(); i++){
					int user_id = (int) new_user_data.instance(i).value(0);
					new_film_info[user_id] = new Info(user_id);
				}
				
				for(int i = 0; i < old_user_data.numInstances(); i++){
					int user_id = (int) old_user_data.instance(i).value(0);
					old_info[user_id] = new Info(user_id);
				}
				
				
				//update info matrix
				for(int i = 0; i < rating.numInstances(); i++){
					int user_id = (int) rating.instance(i).value(0);
					new_film_info[user_id].addRating((int)rating.instance(i).value(2));
				}
				
				for(int i = 0; i < rating_true.numInstances(); i++){
					int user_id = (int) rating_true.instance(i).value(0);
					old_info[user_id].addRating((int)rating_true.instance(i).value(2));
				}
				
		
		/*
		System.out.println("Mapping");
		System.out.println(mapping[100].id_new + "  " + mapping[100].id_old );
		System.out.println(mapping[200].id_new + "  " + mapping[200].id_old );
		System.out.println(mapping[300].id_new + "  " + mapping[300].id_old );
		System.out.println(mapping[400].id_new + "  " + mapping[400].id_old );
		*/
		System.out.println(test_user);
		System.out.println(count);
	}
}
