package Classificatore;
import java.util.Arrays;
import librerie_Aggiunte.Roba_utile;
import weka.core.FastVector;
import weka.core.Instances;

public class Most_popular {
	
	public static Frequency[] calc_frequency_relevant(Instances data) throws Exception{
		
		
		//DataSource join = new DataSource("/src/Dati/super-joinsenzaGenere.arff");
		
		String film_source = new String("src/Dati/movieMeta+anno.arff");
		Instances film = Roba_utile.load(film_source);
		
		Frequency[] table = new Frequency[(int)film.lastInstance().value(0)];
		
		for(int i = 1; i <= table.length; i++){
			table[i-1] = new Frequency(i);
		}
		
		//calcolo occorrenze sopra il 4
		for(int i = 0; i < data.numInstances(); i++){
			if(data.instance(i).value(4) >= 4){
				table[ (int) data.instance(i).value(0)-1].add_occ();
				
			}
		}
		
		Arrays.sort(table);
		//calcolo frequenza
		for(int i = 0; i < table.length; i++){
			table[i].calcFreq(data.numInstances());
		}
		
		return table;
	}
	
	public static Frequency[] calc_frequency(Instances data) throws Exception{
		
		
		//DataSource join = new DataSource("/src/Dati/super-joinsenzaGenere.arff");
		
		String film_source = new String("src/Dati/movieMeta+anno.arff");
		Instances film = Roba_utile.load(film_source);
		
		Frequency[] table = new Frequency[(int)film.lastInstance().value(0)];
		
		for(int i = 1; i <= table.length; i++){
			table[i-1] = new Frequency(i);
		}
		
		//calcolo occorrenze
		for(int i = 0; i < data.numInstances(); i++){
			table[ (int) data.instance(i).value(0)-1].add_occ();
		}
		
		Arrays.sort(table);
		//calcolo frequenza
		for(int i = 0; i < table.length; i++){
			table[i].calcFreq(data.numInstances());
		}
		
		return table;
	}

	public static Instances[] genere_split(Instances data) throws Exception{
		int splits = 2;
		int att_genere = 5;
		
		//data structure
		FastVector fv = new FastVector(data.numAttributes());
		for(int i = 0; i < data.numAttributes(); i++){
			fv.addElement(data.attribute(i));
		}
		
		Instances[] split = new Instances[splits];
		for(int i = 0; i < split.length; i++){
			split[i] = new Instances("rel", fv, data.numInstances());
		}
		
		//genere = F o M
		for(int i = 0; i < data.numInstances(); i++ ){
			if(data.instance(i).stringValue(att_genere).equalsIgnoreCase("M") )
				split[Genere.M.value].add(data.instance(i));
			else
				if(data.instance(i).stringValue(att_genere).equalsIgnoreCase("F") )
					split[Genere.F.value].add(data.instance(i));
				else
					throw new RuntimeException();
		}
		
		return split;
		

	}
		
	public static Instances[] age_split(Instances data) throws Exception{
		int splits = 6;
		int attr_age = 6;
		
		//data structure
		FastVector fv = new FastVector(data.numAttributes());
		for(int i = 0; i < data.numAttributes(); i++){
			fv.addElement(data.attribute(i));
		}
		
		Instances[] split = new Instances[splits];
		for(int i = 0; i < split.length; i++){
			split[i] = new Instances("rel", fv, data.numInstances()/5);
		}
		
		//age=18
		for(int i = 0; i < data.numInstances(); i++ ){
			if(data.instance(i).value(attr_age) == 18)
				split[0].add(data.instance(i));
		}
		
		//age=25
		for(int i = 0; i < data.numInstances(); i++ ){
			if(data.instance(i).value(attr_age) == 25)
				split[1].add(data.instance(i));
		}
		//age = 35
		for(int i = 0; i < data.numInstances(); i++ ){
			if(data.instance(i).value(attr_age) == 35)
				split[2].add(data.instance(i));
		}
		//age = 45
		for(int i = 0; i < data.numInstances(); i++ ){
			if(data.instance(i).value(attr_age) == 45)
				split[3].add(data.instance(i));
		}
		//age = 50
		for(int i = 0; i < data.numInstances(); i++ ){
			if(data.instance(i).value(attr_age) == 50)
				split[4].add(data.instance(i));
		}
		//age = 56
		for(int i = 0; i < data.numInstances(); i++ ){
			if(data.instance(i).value(attr_age) == 56)
				split[5].add(data.instance(i));
		}
		
		return split;
	}
	
	public static Instances[] occupation_split(Instances data) throws Exception{
		int splits = 21;
		int attr_occupation = 7;
		
		//data structure
		FastVector fv = new FastVector(data.numAttributes());
		for(int i = 0; i < data.numAttributes(); i++){
			fv.addElement(data.attribute(i));
		}
		
		Instances[] split = new Instances[splits];
		for(int i = 0; i < split.length; i++){
			split[i] = new Instances("rel", fv, data.numInstances()/5);
		}
		
		for(int i = 0; i < data.numInstances(); i++ ){
			split[(int)data.instance(i).value(attr_occupation)].add(data.instance(i));		
		}
				
		return split;
	}
}
