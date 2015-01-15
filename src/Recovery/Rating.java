package Recovery;

public class Rating implements Comparable<Rating>{
	public int movie;
	public int rating;
	
	public Rating(int movie, int rating){
		this.movie = movie;
		this.rating = rating;
	}

	@Override
	public int compareTo(Rating other) {
		if(this.movie == other.movie && this.rating == other.rating){
			return 0;
		}
		return 1;
	}

}
