package org.example;

import java.sql.Connection;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnector {
	public Connection connect() {
		try {
			String dbURL = "jdbc:sqlserver://kickapps.database.windows.net:1433;database=KickApps;user=KickApps@kickapps;password={Cse201Server};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
			String user = "KickApps@kickapps";
			String pass = "Cse201Server";
			Connection conn = DriverManager.getConnection(dbURL, user, pass);
			return conn;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		Connection conn = connect();
		if (conn != null) {
			try {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				Statement select = conn.createStatement();
				// Get the table of the professors
				rs = select.executeQuery(query);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return rs;
	}

	public ResultSet executeUpdate(String query) {
		ResultSet rs = null;
		Connection conn = connect();
		if (conn != null) {
			try {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(query);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return rs;
	}

	public ResultSet getProfessorResultSet(Connection conn) {
		return executeQuery("Select * From tblProfessors");
	}
	public ResultSet getProfessorFromID(Connection conn, int profID) {
		return executeQuery("Select * From tblProfessors where profID = " + profID);
	}

	public ResultSet getProfComments(Connection conn, int profID) {
		return executeQuery("Select comment from tblComments where profID = " + profID);
	}

	public ResultSet getProfileFromLogin(Connection conn, String usernameField, String passwordField) {
		return executeQuery("select profileID from tblProfiles where username = '" + usernameField
				+ "' and password = '" + passwordField + "'");
	}

	public ResultSet getProfileFromID(Connection conn, int profileID) {
		return executeQuery("Select * from tblProfiles where profileID = " + profileID);
	}

	public void confirmSubmission(Connection conn, int subID, int profID, int profileID, String firstName,
		String lastName, double rateProfScore, String college, String position, int yearsWorked, String degree) {
		String query = "UPDATE tblAdmissions SET subType = 'true' where subID = " + subID;
		executeUpdate(query);
		query = "INSERT INTO tblProfessors VALUES('" + profID + "," + profileID + "," + firstName + "," + lastName + "," + rateProfScore + ","
				+ college + "," + position + "," + yearsWorked + "," + degree + "', 0)";
		executeUpdate(query);
	}

	public ResultSet getSubmissionsResultSet(Connection conn, int profileID) {
		return executeQuery("Select * From tblAdmissions WHERE profileID = " + profileID);
	}
	
	public void submit(Connection conn, int profileID, String firstName, String LastName, double rateProfScore, String college, String position, int yearsWorked,
			String degree) {
		System.out.print(profileID);
		String query = "INSERT INTO tblAdmissions (firstName, LastName, rateProfScore, college, position, yearsWorked, degree, stat, adminID, profileID) "
				+ String.format("VALUES('%s', '%s', %.2f, '%s', '%s', %d, '%s', 'pending', 0, %d)", firstName, LastName, rateProfScore, college, position, yearsWorked, degree, profileID);
		executeUpdate(query);
	}

	public ObservableList<Professor> getSubmissionsObservableList(ResultSet rs) {
		ObservableList<Professor> professors = FXCollections.observableArrayList();
		try {
			while (rs.next()) {
				if (rs.getString(9) == null) {
					professors.add(new Professor(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getDouble(5),
							rs.getString(6), rs.getString(7), rs.getInt(8), "", rs.getString(10)));
				} else {
					professors.add(new Professor(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getDouble(5),
							rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return professors;
	}

	public ObservableList<Professor> getProfessorsObservableList(ResultSet rs) {
		ObservableList<Professor> professors = FXCollections.observableArrayList();
		try {
			while (rs.next()) {
				if (rs.getString(9) == null) {
					professors.add(new Professor(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getDouble(5),
							rs.getString(6), rs.getString(7), rs.getInt(8), ""));
				} else {
					professors.add(new Professor(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getDouble(5),
							rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return professors;
	}
}
