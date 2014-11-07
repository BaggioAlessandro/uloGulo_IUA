package PreProcessing;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import librerie_Aggiunte.Roba_utile;

public class Matrix {
	
	public static void main(String[] args) throws Exception{
		String train_path = new String("src/Dati/train.arff");
		String user_path = new String("src/Dati/userMeta.arff");
		String movie_path = new String("src/Dati/movieMeta+anno.arff");
		
		Instances train = Roba_utile.load(train_path);
		Instances user = Roba_utile.load(user_path);
		Instances movie = Roba_utile.load(movie_path);
				
		FastVector fv = new FastVector((int)movie.lastInstance().value(0));
		for(int i = 0; i < (int)movie.lastInstance().value(0); i++){
			fv.addElement(new Attribute(""+i));
		}
		
		Instances matrix = new Instances("rel", fv, (int)user.lastInstance().value(0));
		
		for(int i = 0; i < (int)user.lastInstance().value(0); i++){
			matrix.add(new Instance((int)movie.lastInstance().value(0)));
		}
		
		System.out.println();
		
		for(int i = 0; i < train.numInstances(); i++){
			int utente = (int) train.instance(i).value(0);
			int k = (int) train.instance(i).value(1);
			double value = train.instance(i).value(2);
			matrix.instance(utente-1).setValue(k-1, value);
			
		}
		
		Roba_utile.save(matrix, "src/Dati/matrix.arff");
		
		for(int i = 1; i < user.numAttributes(); i++){
			matrix.insertAttributeAt(user.attribute(i), matrix.numAttributes());
		}
		
		for(int i = 0; i < user.numInstances(); i++){
			int user_id = (int)user.instance(i).value(0);
			matrix.instance(user_id-1).setValue(matrix.numAttributes()-1, user.instance(i).value(3));
			matrix.instance(user_id-1).setValue(matrix.numAttributes()-2, user.instance(i).value(2));
			matrix.instance(user_id-1).setValue(matrix.numAttributes()-3, user.instance(i).stringValue(1));
		}
		
		Roba_utile.save(matrix, "src/Dati/matrix_prova.arff");
		
	}

}
