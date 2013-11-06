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
                        +" and tr.type_id = t1.type_id");
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
                        +" and tr.type_id = t1.type_id");
                printResults(rs, "name", "health_care_no", "test_name", "test_date", "result");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void searchPerscribe(String employee, String sDate, String eDate){
        try{
            ResultSet rs = this.connection.executeQuery(
                    "Select p1.name, p1.health_care_no, t1.test_name, tr.prescribe_date, tr.employee_no"
                            +" from patient p1, test_type t1, test_record tr"
                            +" where tr.employee_no = "+employee
                            +" and tr.prescribe_date >= to_date('"+sDate+"', 'dd-mon-yyyy')"
                            +" and tr.prescribe_date <= to_date('"+eDate+"', 'dd-mon-yyyy')"
                            +" and p1.health_care_no = tr.patient_no"
                            +" and tr.type_id = t1.type_id");
            printResults(rs, "name", "health_care_no", "test_name", "prescribe_date", "employee_no");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchAlarmAge(String testTypeName){

        try{
            connection.executeQuery("DROP VIEW medical_risk");
        }catch (Exception e){
            System.out.print("TAble ddoesnt exit");
        }
        try{
        connection.executeQuery("CREATE VIEW medical_risk(medical_type,alarming_age,abnormal_rate) AS\n" +
                "SELECT c1.type_id,min(c1.age),ab_rate\n" +
                "FROM  \n" +
                "      -- Find the abnormal rate for each test type\n" +
                "     (SELECT   t1.type_id, count(distinct t1.patient_no)/count(distinct t2.patient_no) ab_rate\n" +
                "      FROM     test_record t1, test_record t2\n" +
                "      WHERE    t1.result <> 'normal' AND t1.type_id = t2.type_id\n" +
                "      GROUP BY t1.type_id\n" +
                "      ) r,\n" +
                "-- Find the abnormal result count above each age\n" +
                "     (SELECT   t1.type_id,age,COUNT(distinct p1.health_care_no) AS ab_cnt\n" +
                "      FROM     patient p1,test_record t1,\n" +
                "               (SELECT DISTINCT trunc(months_between(sysdate,p1.birth_day)/12) AS age FROM patient p1) \n" +
                "      WHERE    trunc(months_between(sysdate,p1.birth_day)/12)>=age\n" +
                "               AND p1.health_care_no=t1.patient_no\n" +
                "               AND t1.result<>'normal'\n" +
                "      GROUP BY age,t1.type_id\n" +
                "      ) c1, \n" +
                " --- Find the patient count above each age\n" +
                "      (SELECT  t1.type_id,age,COUNT(distinct p1.health_care_no) AS cnt\n" +
                "       FROM    patient p1, test_record t1,\n" +
                "             (SELECT DISTINCT trunc(months_between(sysdate,p1.birth_day)/12) AS age FROM patient p1)\n" +
                "       WHERE trunc(months_between(sysdate,p1.birth_day)/12)>=age\n" +
                "             AND p1.health_care_no=t1.patient_no\n" +
                "       GROUP BY age,t1.type_id\n" +
                "      ) c2\n" +
                "WHERE  c1.age = c2.age AND c1.type_id = c2.type_id AND c1.type_id = r.type_id \n" +
                "       AND c1.ab_cnt/c2.cnt>=2*r.ab_rate\n" +
                "GROUP BY c1.type_id,ab_rate");
        }catch (Exception e){
            e.printStackTrace();
        }try{
            ResultSet rs = connection.executeQuery("SELECT DISTINCT health_care_no, address, phone\n" +
                    "FROM   patient p, medical_risk m, test_type tt\n" +
                    "WHERE  trunc(months_between(sysdate,birth_day)/12) >= m.alarming_age \n" +
                    "AND\n" +
                    "       p.health_care_no NOT IN (SELECT patient_no\n" +
                    "                                FROM   test_record t, test_type tt\n" +
                    "                                WHERE  m.medical_type = t.type_id\n" +
                    "                               )"+
                    " AND m.medical_type = tt.type_id"+
                    " AND tt.test_name like"+"'%"+testTypeName+"%'");
            printResults(rs, "health_care_no", "address", "phone");
        }catch (Exception e){
            e.printStackTrace();
        }
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
                System.out.println("\n\n");
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
        System.out.println("\n\n");
        System.out.println("Results in the following format:");
        System.out.println(title);
        System.out.println(underline);
        System.out.println(wholeLine);
        System.out.println("\n\n");

    }

}
