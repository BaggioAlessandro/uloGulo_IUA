package PreProcessing;

import librerie_Aggiunte.Roba_utile;
import weka.core.FastVector;
import weka.core.Instances;

public class TestSet {

	public static void main(String[] args) throws Exception {
		String user_path = new String("src/Dati/userMeta.arff");
		String test = new String("src/Dati/test.arff");

		Instances user_data = Roba_utile.load(user_path);
		
		Instances test_data = Roba_utile.load(test);
		
		FastVector fv = new FastVector(user_data.numAttributes());
		for(int i = 0; i < user_data.numAttributes(); i++){
			fv.addElement(user_data.attribute(i));
		}
		
		Instances test_final = new Instances("rel", fv, test_data.numInstances());

		for(int i = 0; i < test_data.numInstances(); i++){
			for(int j = 0; j < user_data.numInstances(); j++){
				if(test_data.instance(i).value(0) == user_data.instance(j).value(0)){
					test_final.add(user_data.instance(j));
				}
			}
		}
		
		Roba_utile.save(test_final, "src/Dati/test_user.arff");
	}

}
