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

	public ResultSet getProfessorResultSet(Connection conn) {
		ResultSet rs = null;
		if (conn != null) {
			try {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				Statement select = conn.createStatement();
				// Get the table of the professors
				rs = select.executeQuery("Select * From tblProfessors");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return rs;
	}
}
