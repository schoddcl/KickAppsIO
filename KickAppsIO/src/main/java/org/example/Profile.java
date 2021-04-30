package org.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Profile {

	private String username;
	private String password;
	private String permission;

	
	public Profile(String username, String password, String permission) {
		setUsername(username);
		setPassword(password);
		setPermission(permission);
	}
	
	public ResultSet connect(String query, boolean isQuery) {
		// Connect to database
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			String dbURL = "jdbc:sqlserver://kickapps.database.windows.net:1433;database=KickApps;user=KickApps@kickapps;password={Cse201Server};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
			String user = "KickApps@kickapps";
			String pass = "Cse201Server";
			Connection conn = DriverManager.getConnection(dbURL, user, pass);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				
				Statement statement = conn.createStatement();
				
				if(isQuery) {
					// Get the table of the professors
					rs = statement.executeQuery(query);
					return rs;
				}
				statement.executeUpdate(query);
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPermission() {
		return password;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
