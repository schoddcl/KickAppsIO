package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

	public Admin(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public ResultSet accessSubmissions() {
		String query = "Select * From tblAdmissions";
		ResultSet rs = connect(query, true);
		return rs;
	}
	
	public void confirmSubmission(int subID, int profID, int profileID, String firstName, 
			String lastName, double rateProfScore, String college, String position, int yearsWorked, String degree) {
		String query = "UPDATE tblAdmissions SET subType = 'true' where subID = " + subID;
		connect(query, false);
		query = "INSERT INTO tblProfessors VALUES('" + profID + "," + profileID + "," + firstName + "," + lastName + "," + rateProfScore + ","
				+ college + "," + position + "," + yearsWorked + "," + degree + "', 0)";
		connect(query, false);
	}
	
	public void denySubmission(int subID) {
		String query = "UPDATE tblAdmissions SET subType = 'false' where subID = " + subID;
		connect(query, false);
	}
}
