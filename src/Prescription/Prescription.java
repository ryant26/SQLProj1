package Prescription;

import java.util.*;
import java.sql.*;
import Connector.*;

public class Prescription {
	public Prescription(DBConnector con) {
		Statement stmt = null;
		Scanner user_input = new Scanner( System.in );
		
		System.out.print("Employee Number or Name of Doctor:");
		String doctor_id = user_input.next();
		
		System.out.println("Name of test:");
		String test_name = user_input.next();
		
		System.out.println("Patient name or number:");
		String patient_id = user_input.next();
		
		user_input.close();

		ResultSet rs = con.executeQuery("");
	}
	
	public static void createPrescription(Connection conn, String doc_id, String p_id, String t_name) {
		
	}
}
