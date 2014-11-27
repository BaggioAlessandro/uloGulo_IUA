package librerie_Aggiunte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instances;

public class Roba_utile {
	public static Instances load(String filename) throws Exception {
	     Instances       result;
	     BufferedReader  reader;
	     reader = new BufferedReader(new FileReader(filename));
	     result = new Instances(reader);
	     result.setClassIndex(result.numAttributes() - 1);
	     reader.close();
	 
	     return result;
	 }
	
	public static void save(Instances data, String filename) throws Exception {
	     BufferedWriter  writer;
	 
	     writer = new BufferedWriter(new FileWriter(filename));
	     writer.write(data.toString());
	     writer.newLine();
	     writer.flush();
	     writer.close();
	   }
	
	public static boolean visto(int id_user, int id_film, Instances data){
		for(int i = 0; i < data.numInstances(); i++){
			if(data.instance(i).value(0) == id_user)
				if(data.instance(i).value(4) == id_film){
					System.out.println("visto");
					return true;
				}
			
		}
		return false;
	}
}
