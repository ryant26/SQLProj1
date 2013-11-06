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
        ResultSet rs;
        if (m.find()){
            //We have a patient #
            try{
                rs = this.connection.executeQuery(
                        "Select p1.name, p1.health_care_no, t1.test_name, tr.test_date, tr. result"
                        +" from patient p1, test_type t1, test_record tr"
                        +" where p1.health_care_no = "+searchString
                        +" and p1.health_care_no = tr.patient_no"
                        +" and tr.test_id = t1.type_id");
                printResults(rs, "name", "health_care_no", "test_name", "test_date", "result");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            //We have a name
            try{
                rs = this.connection.executeQuery(
                        "select p1.name, p1.health_care_no, t1.test_name, tr.test_date, tr. result"
                        +" from patient p1, test_type t1, test_record tr"
                        +" where p1.name like"+"'%"+searchString+"%'"
                        +" and p1.health_care_no = tr.patient_no"
                        +" and tr.test_id = t1.type_id");
                printResults(rs, "name", "health_care_no", "test_name", "test_date", "result");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void searchPerscribe(String employee, String sDate, String eDate){
        try{
            ResultSet rs = this.connection.executeQuery(
                    "Select p1.name, p1.health_care_no, t1.test_name, tr.test_date, tr.prescribe_date"
                            +" from patient p1, test_type t1, test_record tr"
                            +" where tr.employee_no = "+employee
                            +" and p1.health_care_no = tr.patient_no"
                            +" and tr.test_id = t1.type_id");
            printResults(rs, "name", "health_care_no", "test_name", "prescribe_date");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchAlarmAge(){
        //Code Here
    }

    private void printResults(ResultSet rs, String ... argList){
        //Variables we need
        String title = "";
        String underline = "";
        int tabCounter = 0;

        String wholeLine = "";

        //Start Printing the result set
        try{
            while (rs.next()){
                for (String i : argList){
                    String result = rs.getString(i);
                    wholeLine += (result + String.format("%"+5+"s", ""));
                }
                wholeLine += "\n\n";

            }
            if (wholeLine.length() == 0){
                System.out.println("No results match your query!");
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        //Get the title, and print it out with an underline
        for (String i : argList){
            title += i + String.format("%"+5+"s", "");
        }

        for (int i = 0; i<title.length(); i++){
            underline += "-";
        }
        System.out.println("Results in the following format:");
        System.out.println(title);
        System.out.println(underline);
        System.out.println(wholeLine);

    }

}
