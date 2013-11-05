package Prescription;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

import Connector.*;

public class Prescription {
	
	private DBConnector conn;

	public Prescription(DBConnector con) {
		conn = con;
	}
	public void createPrescription() throws SQLException {
		Statement stmt = null;
		Scanner user_input = new Scanner( System.in );
		user_input.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Employee Number or Name of Doctor:");
		String doctor_name = null;
		String patient_name = null;
		String doctor_id = user_input.next();
		System.out.println(doctor_id);
		
		Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(doctor_id);
		if (m.find()) {
			doctor_name = doctor_id;
		}
		
		System.out.println("Name of test:");
		String test_name = user_input.next();
		ResultSet test_rs = this.conn.executeQuery("Select t.type_id "
				+ " From test_type t "
				+ " Where t.test_name LIKE 'X Ray'");
		ResultSetMetaData	rsetMetaData	=	test_rs.getMetaData();
		System.out.println(rsetMetaData.getColumnName(1));
		Object o	=	test_rs.getObject(1);
		System.out.println(o.toString());
		try {
			if (!test_rs.next()) {
				System.out.println("Invalid test name, please try again");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Patient name or number:");
		String patient_id = user_input.next();
		
		Matcher m2 = p.matcher(patient_id);
		if (m2.find()) {
			patient_name = patient_id;
		}
		
		user_input.close();

		ResultSet rs = this.conn.executeQuery("Select n.health_care_no, n.type_id"
				+ " from not_allowed n"
				+ " where n.type_id = test_name and n.health_care_no = patient_id");
		try {
			if (rs.next()) {
				System.out.println("This combination is not allowed, please try again");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(rs);
	}
	

}
