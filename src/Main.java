/**
 * User: Ryan
 * Date: 2013-11-04
 * Time: 6:21 PM
 */
import Connector.*;
import Search.*;

public class Main {
    public static void main(String[] args) {
        MovieDB temp = new MovieDB();
        temp.createDB();
        temp.insertMovies();
        temp.close();
    }
}
