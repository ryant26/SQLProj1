import java.sql.*;


public class DBConnector {
	
	private Connection con; 
	
	public DBConnector(String url, String user, String pass) {
		String m_driverName = "oracle.jdbc.driver.OracleDriver";
		try {
			Class drvClass = Class.forName (m_driverName);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver unable to load");
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public void closeConnection () {
        try {
                con.close(); 
        }
        catch (Exception e) {
                e.printStackTrace();
        }
}
    public Boolean executeNonQuery(String statement)  {
        try {
                Statement stmt = con.createStatement();
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
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                return rs;
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
        return null;
}
}
