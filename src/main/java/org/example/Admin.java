package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

	public Admin(String username, String password, String permission) {
		super(username, password, permission);
		// TODO Auto-generated constructor stub
	}

	public ResultSet accessSubmissions() {
		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
		return connector.getAllSubmissionsResultSet(conn);
	}

	public boolean confirmSubmission(Professor prof) {
		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
 		connector.confirmSubmission(conn, prof);
 		return true;
	}

	public boolean denySubmission(Professor prof) {
		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
 		connector.denySubmission(conn, prof);
 		return true;
	}
}
