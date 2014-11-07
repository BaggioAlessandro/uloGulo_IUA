package PreProcessing;

public enum Generi_base {
	Drama,
	Thriller,
	Crime,
	Comedy,
	Action,
	Sci_Fi,
	Documentary,
	Western,
	Romance,
	Horror,
	Mystery,
	Adventure,
	Children_s,
	War,
	Animation,
	Film_Noir,
	Fantasy,
	Musical;
	
	public static boolean in(String string){
		
		for(int i = 0; i < Generi_base.values().length; i++){
			if(values()[i].toString().equalsIgnoreCase(string)){
				return true;
			}
		}
		return false;
	}
	
	
}
