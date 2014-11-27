package Recovery;

public class Info implements Comparable<Info>{
	public int numRating;
	public int num1;
	public int num2;
	public int num3;
	public int num4;
	public int num5;
	
	public int id;
	
	public Info(int id){
		this.id = id;
	}
	
	public void addRating(int value){
		switch(value){
			case 1: num1++;
					numRating++;
					break;

			case 2: num2++;
					numRating++;
					break;

			case 3: num3++;
					numRating++;
					break;

			case 4: num4++;
					numRating++;
					break;

			case 5: num5++;
					numRating++;
					break;
		}
	}
	
	@Override
	public int compareTo(Info other) {
		if(this.numRating != other.numRating){
			return 1;
		}
		
		if(this.num1 == other.num1 && this.num2 == other.num2 && this.num3== other.num3 && this.num4 == other.num4 && this.num5 == other.num5){
			return 0;
		}else 
			return 1;
	}
}
