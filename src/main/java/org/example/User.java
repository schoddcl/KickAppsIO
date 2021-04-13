package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class User extends Profile {

	public User(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public ResultSet viewProfessors(int profID, String firstName, String lastName) {
		//prof id for database
		//username made up of 
		String query = "Select * From tblProfessors WHERE profID = " + profID + " AND firstName LIKE '" + firstName + "' AND lastName LIKE '" + lastName + "'";
		return connectDatabase(query, true);
		
	}

	public void submit(int profID, double rateMyProfessorScore, String college, String position, int yearsWorked,
			String classes, String degree, boolean update) {
		String query = "INSERT INTO tblAdmissions VALUES('"  + college + "', 0)";
		connectDatabase(query, false);
		
	}

	public void updateComments(String comment, int profID) {
		String query = "UPDATE tblComments SET comment = comment + '\n" + comment + "' where profID = " + profID;
		connectDatabase(query, false);
		//separate Strings by new line at the beginning of each entry
	}
}