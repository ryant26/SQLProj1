package Search;
import Connector.DBConnector;

/**
 * User: Ryan
 * Date: 2013-11-04
 * Time: 7:59 PM
 */
public class SearchCon {
    DBConnector connection;
    public SearchCon(DBConnector conn){
        this.connection = conn;
    }
}
