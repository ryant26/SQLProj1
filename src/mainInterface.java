import java.sql.*;
import java.util.*;

import Connector.*;
import Prescription.*;
import Search.*;

public class mainInterface {
	public static void main(String args[]) throws SQLException {

	Scanner user_input = new Scanner( System.in );
	DBConnector con;
	System.out.print("User name: ");
	String m_username = user_input.next();
	System.out.println(" ");
	System.out.print("Password: ");
	String m_password = user_input.next();

	con = new DBConnector("jdbc:oracle:thin:@localhost:1525:CRS", m_username, m_password);

	boolean option = false;
	while (option != true) {

	System.out.println("Options:");
	System.out.println("(1) Prescription");
	System.out.println("(2) Medical Test");
	System.out.println("(3) Patient Information Update");
	System.out.println("(4) Search Engine");
	System.out.println("(5) Exit");

	String type = user_input.next();

	if (type.equals("1")) {
		Prescription p = new Prescription(con);
		p.createPrescription();
		option = true;
		user_input.close();
	}
	else if (type.equals("2")) {
		option = true;
		user_input.close();
	}
	else if (type.equals("3")) {
		option = true;
		user_input.close();
	}
	else if (type.equals("4")) {
        /*
        * Not necessary to set option
        * my interface is self contained, so when it kicks back out,
        * we want to start at the top of the loop
        * */
		searchInterface SI = new searchInterface(con);
		SI.mainLoop();
        continue;
	}
	else if (type.equals("5")) {
		option = true;
		user_input.close();
	}
	else {
		System.out.println("Incorrect option. Please try again");

	}
	}

	}
	public static Connection createConnection(String user, String pass) {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1525:CRS", user, pass);
		} catch (SQLException e) {
			System.out.println("Unable to establish database connection");
			e.printStackTrace();
		}
	return con;
	}
}
