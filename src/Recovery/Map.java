package Recovery;

public class Map {
	public int id_new;
	public int id_old;
	
	public Map(){
		id_new = 0;
		id_old = 0;
	}
	
	public String toString(){
		return new String(id_old + "," + id_new);
	}

}
