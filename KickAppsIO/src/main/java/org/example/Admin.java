package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

	public Admin(String username, String password, String permission) {
		super(username, password, permission);
		// TODO Auto-generated constructor stub
	}

	public ResultSet accessSubmissions() {
		String query = "Select * From tblAdmissions";
		ResultSet rs = connect(query, true);
		return rs;
	}
	
	public void confirmSubmission(int subID, int profileID, String firstName, 
			String lastName, double rateProfScore, String college, String position, int yearsWorked, String degree, String stat, int adminID) {
		String query = "UPDATE tblAdmissions SET stat = 'Confirmed' where subID = " + subID;
		connect(query, false);
		query = "INSERT INTO tblProfessors(profileID, firstName, lastName, rateProfScore, college, position, yearsWorked, degree) "
				+ "VALUES('" + profileID + "','" + firstName + "','" + lastName + "'," + rateProfScore + ",'"
				+ college + "','" + position + "'," + yearsWorked + ",'" + degree + "')";
		connect(query, false);
	}
	
	public void denySubmission(int subID) {
		String query = "UPDATE tblAdmissions SET stat = 'Denied' where subID = " + subID;
		connect(query, false);
	}
}
