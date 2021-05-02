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

	public User(String username, String password, String permission) {
		super(username, password, permission);
		// TODO Auto-generated constructor stub
	}

	public ResultSet viewProfessors(int profID, String firstName, String lastName) {
		//prof id for database
		//username made up of 
		String query = "Select * From tblProfessors WHERE profID = " + profID + " AND firstName LIKE '" + firstName + "' AND lastName LIKE '" + lastName + "'";
		return connect(query, true);
		
	}

	public void submit(int profileID, String firstName, String lastName, double rateMyProfessorScore, String college, String position, int yearsWorked,
			String degree) {
		String query = "INSERT INTO tblAdmissions(profileID, firstName, lastName, rateProfScore, college, position, yearsWorked, degree)"
				+ " VALUES("+ profileID + ", '" + firstName+"', '"+ lastName+ "', " + rateMyProfessorScore+ ", '" + college + "', '" + position + "', "
				+ yearsWorked + ", '" + degree + "')";
		//System.out.println(query);
		connect(query, false);
		
	}

	public void updateComments(String comment, int profID) {
		String query = "UPDATE tblComments SET comment = '" + comment + "' WHERE profID = '" + profID+"'";
		connect(query, false);
		//separate Strings by new line at the beginning of each entry
	}
}
