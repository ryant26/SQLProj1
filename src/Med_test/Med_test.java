package Med_test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

import Connector.DBConnector;

public class Med_test {
	private Scanner scan = new Scanner(System.in);
	public Med_test(){
		
	}
	
	public void mainLoop(DBConnector conn){
		scan.useDelimiter(System.getProperty("line.separator"));
		ResultSet rs = Find_Record(conn);//If this is multiple rows will need to implement a choosing system
		int num_rows = printResults(rs,"test_id", "Patient", "Doctor", "Test", "Date_Prescribed");
		String test_id = null;
		String type_id = null;
		
		if(num_rows>1){
			System.out.println("More than one");
			System.out.println("Please enter the test ID you wish to update: ");
			
			test_id = scan.next();
			type_id = Get_test(test_id, conn);
		}
		else if(num_rows == 0){
			System.out.println("There is no prescription currently in the system with those values\n Please try again: ");
			mainLoop(conn);
		}
		else{
			System.out.println("Just one");
			try {
				rs.first();
				test_id = rs.getString("test_id");
				type_id = rs.getString("type_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Update_Record(test_id, type_id, conn);
	}
	
	private ResultSet Find_Record(DBConnector conn){
		System.out.println("Please Enter the Patient's Name or Health Care Number: ");
		String Id_name_p = scan.next();
		String check_p = ID_Check(Id_name_p);
		
		System.out.println("Please Enter the Prescribing Doctor's name or Employee Number: ");
		String Id_name_d = scan.next();
		String check_d = ID_Check(Id_name_d);
		
		ResultSet rs = null;
		Statement stmt = null;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		if(check_p.equals("Name") && check_d.equals("Name")){
			try{
	            rs = stmt.executeQuery(
	            		"SELECT tr.test_id AS test_id, tr.type_id AS type_id, p.name AS Patient, p1.name AS Doctor,  tt.test_name AS Test, tr.prescribe_date AS Date_Prescribed"
	            		+" FROM patient p, patient p1, doctor d, test_type tt, test_record tr"
	            		+" WHERE tr.patient_no = p.health_care_no"
	            		+" AND p.name LIKE '%"+Id_name_p+"%'"
	            		+" ANd tr.employee_no = d.employee_no"
	            		+" AND p1.health_care_no = d.health_care_no"
	            		+" AND p1.name LIKE '%"+Id_name_d+"%'"
	            		+" AND tt.type_id = tr.type_id");
	        }catch (Exception e){
	            e.printStackTrace();
	        }
		}
		else if(check_p.equals("Name") && check_d.equals("ID")){
			try{
	            rs = stmt.executeQuery(
	            		"SELECT tr.test_id, tr.type_id AS type_id, p.name AS Patient, p1.name AS Doctor,  tt.test_name AS Test, tr.prescribe_date AS Date_Prescribed"
	            		+" FROM patient p, patient p1, doctor d, test_type tt, test_record tr"
	            		+" WHERE tr.patient_no = p.health_care_no"
	            		+" AND p.name LIKE '%"+Id_name_p+"%'"
	            		+" AND d.employee_no= "+Id_name_d
	            		+" ANd tr.employee_no = d.employee_no"
	            		+" AND p1.health_care_no = d.health_care_no"
	            		+" AND tt.type_id = tr.type_id");
	        }catch (Exception e){
	            e.printStackTrace();
	        }
		}
		else if(check_p.equals("ID") && check_d.equals("ID")){
			try{
	            rs = stmt.executeQuery(
	            		"SELECT tr.test_id, tr.type_id AS type_id, p.name AS Patient, p1.name AS Doctor,  tt.test_name AS Test, tr.prescribe_date AS Date_Prescribed"
	            		+" FROM patient p, patient p1, doctor d, test_type tt, test_record tr"
	            		+" WHERE tr.patient_no = p.health_care_no"
	            		+" AND p.health_care_no= " +Id_name_p
	            		+" AND d.employee_no= "+Id_name_d
	            		+" ANd tr.employee_no = d.employee_no"
	            		+" AND p1.health_care_no = d.health_care_no"
	            		+" AND tt.type_id = tr.type_id");
	        }catch (Exception e){
	            e.printStackTrace();
	        }
		}
		else if(check_p.equals("ID") && check_d.equals("Name")){
			try{
	            rs = stmt.executeQuery(
	            		"SELECT tr.test_id, tr.type_id AS type_id, p.name AS Patient, p1.name AS Doctor,  tt.test_name AS Test, tr.prescribe_date AS Date_Prescribed"
	            		+" FROM patient p, patient p1, doctor d, test_type tt, test_record tr"
	            		+" WHERE tr.patient_no = p.health_care_no"
	            		+" AND p.health_care_no= " +Id_name_p
	            		+" AND p1.name LIKE '%"+Id_name_d+"%'"
	            		+" ANd tr.employee_no = d.employee_no"
	            		+" AND p1.health_care_no = d.health_care_no"
	            		+" AND tt.type_id = tr.type_id");
	        }catch (Exception e){
	            e.printStackTrace();
	        }
		}
		
		return rs;//This will be only one Row as the specific row will be chosen by user
		
	}
	
	private void Update_Record(String test_id, String type_id, DBConnector conn){
		System.out.println("Please enter the name of the Medical Lab these\n are being performed at: ");
		String med_lab = scan.next();
		String check = Check_Lab(med_lab, conn);
		if(check.equals("Invalid")){
			System.out.println("Invalid Lab Name");
			Update_Record(test_id, type_id, conn);
		}
		else{
			check = Check_Test(type_id, med_lab, conn);
			if(check.equals("Invalid")){
				System.out.println("This Lab does not have the proper \n credentials to perform this test");
				Update_Record(test_id, type_id, conn);
			}
			else{
				System.out.println("Please enter the result of the test performed: ");
				String res = scan.next();
		
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date today = new Date();
				String date = dateFormat.format(today);
				ResultSet rs = null;
				Statement stmt = null;
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				try{
					rs = stmt.executeQuery(
							"UPDATE test_record "
									+" SET medical_lab= '"+med_lab
									+"', test_date='"+date
									+"', result= '"+res
									+"' WHERE test_id= '"+test_id
									+"'");
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private String Check_Lab(String Check, DBConnector conn){
		ResultSet rs = null;
		Statement stmt = null;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try{
            rs = stmt.executeQuery(
            		"SELECT lab_name "
            		+" FROM medical_lab "
            		+" WHERE lab_name= '"+Check
            		+"'");
            
        }catch (Exception e){
            e.printStackTrace();
        }
		try {
			if(rs.next() == false){
				return "Invalid";
			}
			else{
				return "Valid";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Invalid Data";
		}
	}
	
	private String Get_test(String test_id, DBConnector conn){
		ResultSet rs = null;
		Statement stmt = null;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String type_id = null;
		try{
            rs = stmt.executeQuery(
            		"SELECT type_id "
            		+" FROM test_record "
            		+" WHERE test_id= '"+test_id
            		+"'");
            
        }catch (Exception e){
            e.printStackTrace();
        }
		try {
			rs.first();
			type_id = rs.getString("type_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return type_id;
	}
	
	private String Check_Test(String type_id, String lab_name, DBConnector conn){
		ResultSet rs = null;
		Statement stmt = null;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try{
            rs = stmt.executeQuery(
            		"SELECT * "
            		+" FROM can_conduct "
            		+" WHERE lab_name= '"+lab_name
            		+"' AND type_id= '"+type_id
            		+"'");
            
        }catch (Exception e){
            e.printStackTrace();
        }
		try {
			if(rs.next() == true){
				return "Valid";
			}
			else{
				return "Invalid";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Query Error";
		}
	}
	private String ID_Check(String check){
		String pattern1 = "[a-zA-Z]";
		String pattern2 = "[0-9]";
		Pattern p = Pattern.compile(pattern1);
		Matcher m = p.matcher(check);
		while(m.find()){
			return "Name";
		}
		p = Pattern.compile(pattern2);
		m = p.matcher(check);
		while(m.find()){
			return "ID";
		}
		return null;
	}
	
	private int printResults(ResultSet rs, String ... argList){
		try {
			rs.beforeFirst();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        //Variables we need
        String title = "";
        String underline = "";
        int tabCounter = 0;
        int num_rows = 0;
        String wholeLine = "";

        //Start Printing the result set
        try{
            while (rs.next()){
            	num_rows = num_rows +1;
                for (String i : argList){
                    String result = rs.getString(i);
                    wholeLine += (result + String.format("%"+5+"s", ""));
                }
                wholeLine += "\n\n";

            }
            if (wholeLine.length() == 0){
                System.out.println("No results match your query!");
                System.out.println("\n\n");
            }
        }catch(Exception e){
            e.printStackTrace();

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
        return num_rows;
    }
	
}
