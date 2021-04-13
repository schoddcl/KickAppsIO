package junitTests;



import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement

import org.example.Admin;
import org.junit.jupiter.api.Test;

class AdminTests {

	@Test
	void canCallAccessSubmissions() {
		String result = "";
		Admin a = new Admin("Luke", "Sarrazine");
		ResultSet rs = a.accessSubmissions();
		try {
			while(rs.next()) {
				result = rs.getString(2);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("college", result);
	}
	
	@Test
	void canCallConfirmSubmission() {
		String result = "";
		Admin a = new Admin("Luke", "Sarrazine");
		a.confirmSubmission(4);
		ResultSet rs = connectDatabase("Select * from tblAdmissions where subID = 4", true);
		try {
			while(rs.next()) {
				result = rs.getString(2);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("true", result);
	}
	
	@Test
	void canCallDenySubmission() {
		String result = "";
		Admin a = new Admin("Luke", "Sarrazine");
		a.denySubmission(4);
		ResultSet rs = connectDatabase("Select * from tblAdmissions where subID = 4", true);
		try {
			while(rs.next()) {
				result = rs.getString(2);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("false", result);
	}
	
	public ResultSet connectDatabase(String query, boolean isQuery) {
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
}
