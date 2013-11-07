package Prescription;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import Utilities.RPrinter;

import Connector.*;

public class Prescription {
	
	private DBConnector conn;

	public Prescription(DBConnector con) {
		conn = con;
	}
	public void createPrescription() throws SQLException {
		int id_of_test = -1;
		ResultSet test_rs = null;
		boolean condition = true;
		Scanner user_input = new Scanner( System.in );
		user_input.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Employee Number of Doctor:");
		String doctor_id = user_input.next();
		while(condition) {
		System.out.println("Name of test:");
		String test_name = user_input.next();
		
		test_rs = this.conn.executeQuery("Select t.type_id "
			+ " From test_type t "
			+ " Where t.test_name LIKE '"+test_name+"'");
		RPrinter r = new RPrinter();
		//r.printResults(test_rs,"type_id");
		try {
			if (!test_rs.next()) {
				System.out.println("Invalid test name, please try again");
			}
			else {
				condition = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		//printResults(test_rs, "type_id");
		
		
		System.out.println("Patient health care number:");
		String patient_id = user_input.next();

		
		ResultSet rs = this.conn.executeQuery("SELECT UNIQUE n.health_care_no, n.test_id"
				+ " FROM not_allowed n"
				+ " INNER JOIN test_type t"
				+ " ON "+test_rs.getNString("type_id")+"= n.test_id"
				+ " INNER JOIN patient p"
				+ " ON n.health_care_no ="+patient_id);
		try {
			if (rs.next()) {
				System.out.println("This combination is not allowed, please try again");
				condition = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!condition) {
		Random rand = new Random();
		
		while (id_of_test == -1){
		id_of_test = rand.nextInt(1000);
		ResultSet rs2 = this.conn.executeQuery("SELECT test_id from test_record"
				+ " where test_id = "+id_of_test);
		if (rs2.next()) {
			//failed to generate proper test ID
			System.out.println("Trial test ID is "+id_of_test);
			id_of_test = -1;
		}
		}
		System.out.println("Proper ID of test is "+id_of_test);
		String temp = test_rs.getNString("type_id");
		System.out.println("Temp is "+temp);
		String sql = "INSERT INTO test_record" + 
					" VALUES ("+id_of_test+", "+test_rs.getNString("type_id")+""
							+", "+patient_id+" , "+doctor_id+", null,"
								+"	null,null,null )";
		boolean insert = this.conn.executeNonQuery(sql);
		System.out.println(""+insert+": Record was stored in database");
		ResultSet worksmaybe = this.conn.executeQuery("SELECT * FROM test_record");
		RPrinter r = new RPrinter();
		r.printResults(worksmaybe, "test_id", "type_id", "patient_no", "employee_no", "medical_lab",
				"result", "prescribe_date", "test_date");
		
		}
		
	}
	

}
