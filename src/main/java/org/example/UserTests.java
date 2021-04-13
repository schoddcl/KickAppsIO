

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement


import org.junit.jupiter.api.Test;

class UserTests {

	@Test
	void canCallViewProfessors() {
		String firstName = "";
		User u = new User("Luke", "Sarrazine");
		ResultSet rs = u.viewProfessors(1, "Matthew", "Stephan");
		try {
			while(rs.next()) {
				firstName = rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals("Matthew", firstName);
	}

	@Test
	void canCallSubmit() {
		String result = "";
		User u = new User("Luke", "Sarrazine");
		u.submit(0, 5.0, "Miami University", "Professor", 2, "CSE201", "PHD", false);
		ResultSet rs = connectDatabase("Select * from tblAdmissions where adminID = 0", true);
		try {
			while(rs.next()) {
				result = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals("Miami University", result);
	}
	
	@Test
	void canCallUpdateComments() {
		String result = "";
		User u = new User("Luke", "Sarrazine");
		String query = "INSERT INTO tblComments VALUES(3, 0, 'comment')";
		connectDatabase(query, false);
		u.updateComments("testComment", 1);
		ResultSet rs = connectDatabase("Select * from tblComments where profID = 0", true);
		try {
			while(rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.print(result);
		assertEquals("Miami University", result);
		
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
