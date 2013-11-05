package Search;
import  Connector.*;
import java.util.regex.*;
import java.sql.*;

public class Search extends SearchCon{

    public Search(DBConnector conn){
        super(conn);
    }

    public void searchTestResults(String searchString){
        Pattern p = Pattern.compile("\\d+?");
        Matcher m = p.matcher(searchString);
        if (m.find()){
            //We have a patient #
            ResultSet rs = this.connection.executeQuery(
                    "Select p1.name, p1.health_care_no, t1.test_name, tr.test_date, tr. result"
                    + " from patient p1, test_type t1, test_record tr"
                    +" where health_Care_no like " + searchString
                    +" and p1.health_care_no = tr.patient_no"
                    +" and tr.test_id = t1.type_id");
        }else{
            //We have a name
            ResultSet rs = this.connection.executeQuery("Select name from patient" +
                    "where name like " + searchString);
        }
    }

    public void searchPerscribe(String employee, String sDate, String eDate){
        //Code Here
    }

    public void searchAlarmAge(){
        //Code Here
    }

}
