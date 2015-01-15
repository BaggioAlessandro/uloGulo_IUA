package Recovery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import weka.core.Instances;
import librerie_Aggiunte.Roba_utile;

public class Recovery {

	public static Info[] new_info;
	static Info[] old_info;
	static Map[] user_mapping;
	static Map[] film_mapping;
	static List<Integer> mapped;
	
	public static void main(String[] args) throws Exception {
		int numUser = 6500;
		
		String path = new String("DataMisc/Recover/");
		
		Instances rating_true = Roba_utile.load(path+"ratings_true.arff");
		Instances rating = Roba_utile.load(path+"train.arff");
		
		Instances old_user_data = Roba_utile.load(path+"users_true.arff");
		Instances new_user_data = Roba_utile.load(path+"userMeta.arff");
	
		film_mapping = mapMovie(path);
		//create structures
		user_mapping = new Map[numUser];
		for(int i = 0; i < numUser; i++){
			user_mapping[i] = new Map();
		}
		
		new_info = new Info[numUser];
		old_info = new Info[numUser];
		
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
			new_info[user_id].addRating((int)rating.instance(i).value(1), (int)rating.instance(i).value(2));
		}
		
		for(int i = 0; i < rating_true.numInstances(); i++){
			int user_id = (int) rating_true.instance(i).value(0);
			old_info[user_id].addRating(film_mapping[(int)rating_true.instance(i).value(1)].id_new,(int)rating_true.instance(i).value(2));
		}
		
		System.out.println("num utenti con n_rating < 20  	" + numTestUser(new_user_data));
		
		//create not mapped list
		mapped = new ArrayList<Integer>(6000);
		//mapping train user
		int count = 0;
		for(int i = 0; i < new_info.length; i++){
			
			if(new_info[i].id != 0 && user_mapping[i].id_new == 0){	
				int inner_count = 0;
				int equals = 0;
				for(int j = 0; j < old_info.length; j++){
					if(new_info[i].compareTo(old_info[j]) == 0 && !mapped.contains(j)){
						inner_count++;
						equals = old_info[j].id;
					}
				}
				if(inner_count == 1){
					user_mapping[i].id_new = i;
					user_mapping[i].id_old = equals;
					mapped.add(equals);
					count++;
				}else{
					if(new_info[i].numRating > 5){
						
						System.out.println("problem");
						System.out.println(new_info[i].numRating);
						System.out.println(i);
						System.out.println(equals);
					}
				}	
			}
		}
		
		//map test user with test equals (2 iterations)
		mapTest();
		mapTest();
		writeCSV(path + "user_mapping.csv", user_mapping);
		writeCSV(path + "film_mapping.csv", film_mapping);
		System.out.println("scritture fatte");
		Instances test_set = Roba_utile.load(path+"test.arff");
		
		checkConsistency();
		
		List<Integer> user_test_list = new ArrayList<>();
		for(int i = 0; i < test_set.numInstances(); i++){
			user_test_list.add((int)test_set.instance(i).value(0));
		}
		
		//delete train user from movielens
		for(int i = 0; i < new_user_data.numInstances();i++){
			int user = (int)new_user_data.instance(i).value(0);
			if(!user_test_list.contains(user)){
				int user_m = user_mapping[user].id_old;
				for(int j = 0; j < rating_true.numInstances(); ){
					if((int)rating_true.instance(j).value(0) == user_m)
						rating_true.delete(j);
					else
						j++;
				}
			}
		}
		
		//delete voti degli user test
		int count_deleting = 0;
		for(int user: user_test_list){
			for(int i = 0; i < rating.numInstances(); i++){
				if(rating.instance(i).value(0) == user){
					int user_m = user_mapping[user].id_old;
					int movie = (int) rating.instance(i).value(1);
					for(int j = 0; j < rating_true.numInstances(); ){
						if((int)rating_true.instance(j).value(0) == user_m && film_mapping[(int)rating_true.instance(j).value(1)].id_new == movie){
							count_deleting++;
							rating_true.delete(j);
						}
						else
							j++;
					}
				}
			}
			System.out.println(user);
		}
		
		//write test rating with movilens ids
		Roba_utile.save(rating_true, path+"test_rating.arff");
		System.out.println("updating");
		//update the id with the new mapping
		for(int i = 0; i < rating_true.numInstances(); i++){
			int film_new = film_mapping[(int)rating_true.instance(i).value(1)].id_new;
			rating_true.instance(i).setValue(1, film_new);
		}
		
		int user_count = 0;
		for(int j = 0; j < rating_true.numInstances(); j++){
			int porcoschifo_count = 0;
			int past = 0;
			for(int i = 0; i < user_mapping.length; i++){
				if(user_mapping[i].id_old == (int)rating_true.instance(j).value(0) && user_mapping[i].id_old!=0){
					int user = user_mapping[i].id_new;
					past = (int)rating_true.instance(j).value(0);
					rating_true.instance(j).setValue(0, user);
					user_count++;
					porcoschifo_count++;
					break;
				}
			}
			if(porcoschifo_count != 1){
				System.out.println("Porco schifo");
				System.out.println(porcoschifo_count);
				System.out.println(rating_true.instance(j).value(0));
				System.out.println(past);
			}
		}
		
		Roba_utile.save(rating_true, path+"rating_mapped.arff");
		
		System.out.println(user_count);
	}

	
	
	
	private static void checkConsistency() {
		for(int i = 0; i < user_mapping.length; i++){
			int count_new = 0;
			int count_old = 0;
			for(int j = 0; j < user_mapping.length; j++){
				if(i!=j && user_mapping[i].id_new!=0){
					if(user_mapping[i].id_new == user_mapping[j].id_new)
						count_new++;
					if(user_mapping[i].id_old == user_mapping[j].id_old)
						count_old++;
				}
			}
			if(count_new > 1){
				System.out.println("problema mapping id_new "+user_mapping[i].id_new + "index " + i + "count = " + count_new);
			}
			if(count_old > 1){
				System.out.println("problema mapping id_old "+user_mapping[i].id_old + "index " + i + "count = " + count_old);
			}
		}
		System.out.println("consistenza finita");
	}

	private static void writeCSV(String file, Map[] mapping) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		StringBuilder sb = new StringBuilder();
		for (Map element : mapping) {
		sb.append(element.toString());
		sb.append("\n");
		}

		br.write(sb.toString());
		br.close();
		
	}

	private static int numTestUser(Instances new_user_data) {
		int test_user = 0;
		for(int i = 0; i < new_user_data.numInstances(); i++){
			int user_id = (int) new_user_data.instance(i).value(0);
			if(new_info[user_id].numRating < 20){
				test_user++;
			}
		}
		return test_user;
	}

	private static void mapTest(){
		int not_mapped = 0;
		for(int i = 0; i < new_info.length; i++){
			
			if(new_info[i].id != 0 && user_mapping[i].id_new == 0){	
				int inner_count = 0;
				int equals = 0;
				for(int j = 0; j < old_info.length; j++){
					if(new_info[i].testEqual(old_info[j]) && !mapped.contains(j)){
						inner_count++;
						equals = old_info[j].id;
					}
				}
				if(inner_count == 1){
					user_mapping[i].id_new = i;
					user_mapping[i].id_old = equals;
					mapped.add(equals);
				}else{
					not_mapped++;
					}
				}
			}
		System.out.println(not_mapped);
	}
	
	private static Map[] mapMovie(String path) throws Exception {
		Instances movie_new = Roba_utile.load(path + "movieMeta.arff");
		Instances movie_old = Roba_utile.load(path + "movies_true.arff");
		
		int numFilm = (int)movie_old.lastInstance().value(0) + 1;

		Map[] map_film = new Map[numFilm];
		for(int i = 0; i < numFilm; i++){
			map_film[i] = new Map();
		}
		
		for(int i = 0; i < movie_old.numInstances(); i++){
			for(int j = 0; j < movie_new.numInstances(); j++){
				if(movie_old.instance(i).stringValue(1).compareTo(movie_new.instance(j).stringValue(1)) == 0){
					map_film[(int)movie_old.instance(i).value(0)].id_new = (int)movie_new.instance(j).value(0);
					map_film[(int)movie_old.instance(i).value(0)].id_old = (int)movie_old.instance(i).value(0);
				}
			}
		}
		return map_film;
	}
}
