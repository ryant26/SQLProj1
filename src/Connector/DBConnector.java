package Connector;

import java.sql.*;

public class DBConnector {
	private Connection con;

    public DBConnector(String url, String user, String pass) throws SQLException{
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
		System.out.println("Invalid username/password combination. Please try again.");
                    throw new SQLException();
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

    public Statement createStatement(int first, int second){
         Statement stmt = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {

            e.printStackTrace();
        }
         return stmt;
    }

    public DatabaseMetaData getMetaData(){
        DatabaseMetaData md = null;
        try{
            md = con.getMetaData();
        }catch(Exception e){
            e.printStackTrace();
        }
        return md;
    }
}
