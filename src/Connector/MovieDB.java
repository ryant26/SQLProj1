package Connector;

//This is a class that uses the DBConnector to create a movie database.

/*
 * Author: Alejandro Ramirez Sanabria (ramirezs@ualberta.ca)
 * Date:   20/OCT/2013
*/

import java.lang.System;

public class MovieDB {

	private DBConnector connector;

	//Class constructor: initializes the DB connection using the specified Oracle DB.
	public MovieDB () {
		System.out.println("Opening connection to Oracle DB...");
        try{
		connector = new DBConnector("jdbc:oracle:thin:@localhost:1525:CRS", "user", "psas");
        }catch (Exception e){

        }
		System.out.println("Connection has been opened successfully!");
	}

	//Closes the connection
	public void close () {
		connector.closeConnection();
		System.out.println("Connection Closed!");
	}

	//Creates the DB schema, which in this case is one table.
	public void createDB () {
		//Let's check if the table exists, and if it does, delete it. Note that this is a little more advance because we are using a transaction and error code (out of the scope so far).
		connector.executeNonQuery("BEGIN EXECUTE IMMEDIATE 'DROP TABLE movie'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;");
		//Create the table
		connector.executeNonQuery("create table movie(title char(20), movie_number integer, primary key(movie_number))");
		System.out.println("DB Tables created!");
	}

	//This method performs three different inserts into the BD
	public void insertMovies () {
		connector.executeNonQuery("insert into movie values ('Movie 1',1)");
		connector.executeNonQuery("insert into movie values ('Movie 2',2)");
		connector.executeNonQuery("insert into movie values ('Movie 3',3)");
	}


}
