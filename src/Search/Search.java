package Search;
import  Connector.*;

import java.sql.*;
import java.util.regex.*;

public class Search {
    private DBConnector connector;

    public Search(DBConnector conn){
        this.connector = conn;
    }

    public void searchForPatient(String searchString){
        Pattern p = Pattern.compile("\\d+?");
        Matcher m = p.matcher(searchString);
        if (m.find()){
            //We have a patient #
        }else{
            //We have a name
        }
    }

    public void searchTestHist(String employee, String sDate, String eDate){
        //Code Here
    }

    public void searchAlarmAge(){
        //Code Here
    }

}
