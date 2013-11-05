package Connector;

public class Main {

	
	public static void main(String[] args) {
		MovieDB temp = new MovieDB();
		temp.createDB();
		temp.insertMovies();
		temp.close();
	}

}
