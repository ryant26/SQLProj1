package Prescription;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
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
	//method to allow input into database, in the form of a
	//new test record
	public void createPrescription() throws SQLException {
		int id_of_test = -1;

		//open up a user_input scanner, used to take input
		//not closed because it will be closed in mainInterface
		Scanner user_input = new Scanner( System.in );

		//change delimiter to a newline character, due to
		//tests possibly having whitespace in them
		user_input.useDelimiter(System.getProperty("line.separator"));

		//get user input and check that everything is valid
		String doctor_id = this.doctor_id_input(user_input);
		ResultSet test_rs = this.test_name_input(user_input);
		String patient_id = this.patient_id_input(user_input);

		//checks not_allowed tables, will throw back to mainInterface if match found
		boolean allowed = this.is_not_allowed(test_rs, patient_id);

		//if everything is valid, then execute query
		if (allowed) {

		Random rand = new Random();

		//generate new random testID that is not already used
		while (id_of_test == -1){
		id_of_test = rand.nextInt(1000);
		ResultSet rs2 = this.conn.executeQuery("SELECT test_id from test_record"
				+ " where test_id = "+id_of_test);
		if (rs2.next()) {
			//failed to generate proper test ID
			id_of_test = -1;
		}
		}


		System.out.println("Generated ID of test is "+id_of_test);


		//set the prescribed date as the current date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String date = dateFormat.format(today);

		//prepare insert statement to create record
		String sql = "INSERT INTO test_record" +
					" VALUES ("+id_of_test+", "+test_rs.getString("type_id")+""
							+", "+patient_id+" , "+doctor_id+", null,"
								+"null, TO_DATE('" +date+ "', 'yyyy-mm-dd'), null)";
		this.conn.executeNonQuery(sql);

		//display full test_record set
		ResultSet full_testrecord = this.conn.executeQuery("SELECT test_id, type_id, patient_no, employee_no, prescribe_date FROM test_record ORDER BY test_id");
		RPrinter r = new RPrinter();
		r.printResults(full_testrecord, "test_id", "type_id", "patient_no", "employee_no", "prescribe_date");

		}

	}

	public String doctor_id_input(Scanner s) throws SQLException {
		//method to take input for doctor employee number
		boolean condition = true;
		ResultSet doctor_rs = null;
		String doctor_id = null;

		//while number not valid (either not a number or not in database)
		while(condition) {
			System.out.print("Employee Number of Doctor:");
			doctor_id = s.next();

			//check if only numbers were input
			Pattern p = Pattern.compile("\\d+?");
	        Matcher m = p.matcher(doctor_id);

	        //if valid number, run query
	        if (m.matches()){

			doctor_rs = this.conn.executeQuery("Select d.employee_no "
					+ " From doctor d "
					+ " Where d.employee_no = "+doctor_id+"");

			if (!doctor_rs.next()) {
				//indicates number not in database
				System.out.println("No such doctor exists, please try again");
				System.out.println();
			}
			else {
				return doctor_id;
			}
	        }
	        else {
	        	//indicates input was not a number
	        	System.out.println("Invalid input, not a valid ID. Please try again");
	        	System.out.println();
	        }
			}
		return null;

	}

	public ResultSet test_name_input(Scanner s) throws SQLException {
		boolean condition = true;
		ResultSet test_rs = null;
		//while loop to allow for correction of test name
				//if input incorrectly
				while(condition) {
				System.out.println("Name of test:");
				String test_name = s.next();

				//query to confirm that the test name is valid
				test_rs = this.conn.executeQuery("Select t.type_id "
					+ " From test_type t "
					+ " Where t.test_name LIKE '"+test_name+"'");


					if (!test_rs.next()) {
						//name didn't match known test name
						System.out.println("Invalid test name, please try again");
						System.out.println();
					}
					else {
						return test_rs;
					}

				}
				return null;

	}

	public String patient_id_input(Scanner s) throws SQLException {
		boolean condition = true;
		String patient_id = null;
		ResultSet patient_rs = null;

		//while no valid number was input
		while(condition) {
		System.out.println("Patient health care number:");
		patient_id = s.next();

		//check to make sure input is a number
		Pattern p2 = Pattern.compile("\\d+?");
        Matcher m2 = p2.matcher(patient_id);

        //if input was number, run query
        if (m2.matches()){

		patient_rs = this.conn.executeQuery("Select p.health_care_no "
				+ " From patient p "
				+ " Where p.health_care_no = "+patient_id+"");

		if (!patient_rs.next()) {
			//invalid number, no patient with that ID
			System.out.println("No such patient exists, please try again");
			System.out.println();
		}
		else {
			return patient_id;
		}
        }
        else {
        	//invalid input, not a number
        	System.out.println("Invalid input, not a valid ID. Please try again");
        	System.out.println();
        }
		}
		return null;
	}

	public boolean is_not_allowed(ResultSet test_rs, String patient_id) throws SQLException {
		//method will indicate if this patient-test combination is allowed

		ResultSet rs = this.conn.executeQuery("SELECT UNIQUE n.health_care_no, n.test_id"
				+ " FROM not_allowed n"
				+ " INNER JOIN test_type t"
				+ " ON "+test_rs.getString("type_id")+"= n.test_id"
				+ " INNER JOIN patient p"
				+ " ON n.health_care_no ="+patient_id);

			if (rs.next()) {
				//combination not allowed, return to mainInterface
				System.out.println("This combination is not allowed, please try again");
				return false;
			}
			else {
				//combination allowed
				return true;
			}


	}


}
