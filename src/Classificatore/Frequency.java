package Classificatore;

public class Frequency implements Comparable<Frequency> {

	public int film_id;
	public int occ;
	public float freq;
	
	public Frequency(int id){
		film_id = id;
		occ = 0;
	}
	
	public void add_occ(){
		occ ++;
	}
	
	public String toString(){
		String output = new String(film_id + ", " + occ+ ", " + freq);
		return output;
	}
	
	public void calcFreq(int num){
		freq = (float)occ/num;
	}

	@Override
	public int compareTo(Frequency arg0) {
		if(this.occ < ((Frequency)arg0).occ)
			return -1;
		
		else if(this.occ == ((Frequency)arg0).occ)
			return 0;
		
		else
			return 1;
	}

}

