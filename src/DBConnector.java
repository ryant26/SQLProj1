import java.sql.*;

/* This class creates a DB Connection and allows the execution of certain SQL statements and queries.
 *
 * For more information, read the lab slides.
 *	 
 * Author: Alejandro Ramirez (ramirezs@ualberta.ca)
 * Date:   20/OCT/2013 
 * */

public class DBConnector {

	//Connection object
	private Connection conn; 
	
	//Class constructor: initializes the connection using Oracle's JDBC
	public DBConnector(String connInfo, String username, String password) {
		
		try {
			String m_driverName = "oracle.jdbc.driver.OracleDriver";
			Class<?> drvClass = Class.forName(m_driverName);
			DriverManager.registerDriver((Driver)drvClass.newInstance());
			conn = DriverManager.getConnection(connInfo, username, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Closes the connection when we are done (remember to ALWAYS close the connection when no longer required).	
	public void closeConnection () {
		try {
			conn.close(); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//A nice method that executes non queries such as inserts, updates, etc.
	//Note: this returns true if successful, false otherwise. 
	
	public Boolean executeNonQuery(String statement)  {
		try {
			Statement stmt = conn.createStatement();
			Boolean rs = stmt.execute(statement);
			return rs;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//This method executes a SQL query and return the ResultSet
	
	public ResultSet executeQuery(String query)  {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	//Note that this method is very application-specific for the movie DB example; this could be made more generic... but that is up to you :)
	
	public String executeQueryAll(String query)  {
		try {
			ResultSet rs = executeQuery(query);
			String result = "Movie ID    Movie Title\n--------------------------------\n";
			while (rs.next()) {
				result += rs.getString("movie_number") + "       " + rs.getString("title") + "\n"; 
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
}
