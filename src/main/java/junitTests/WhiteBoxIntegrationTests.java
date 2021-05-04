package junitTests;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.AddController;
import org.example.Admin;
import org.example.DBConnector;
import org.example.Professor;
import org.example.SubmissionsController;
import org.junit.jupiter.api.Test;

class WhiteBoxIntegrationTests {

	@Test
	void CanUpdateSubmissionsTableFromAddWindow() {
		AddController addController = new AddController();
		assertTrue(addController.setTable(3));
	}
	
	@Test
	void CanUpdateSubmissionsTableFromAdminSubmissionsWindow() {
		SubmissionsController submissionsController = new SubmissionsController();
		assertTrue(submissionsController.setTable(3));
	}
	
	@Test
	void AddingProfessorShowsUpInDatabase() {
		Professor testProf = new Professor(-1, "WhiteBoxTestFirstName", "WhiteBoxTestLastName", 1.0, "WhiteBoxTest", "WhiteBoxTest", 1, "WhiteBoxTest");
		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
		assertTrue(connector.addProfessor(testProf));
		ResultSet rs = connector.getProfessorResultSet(conn);
		String firstName = "";
		String lastName = "";
		try {
			rs.last();
			firstName = rs.getString(3);
			lastName = rs.getString(4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("WhiteBoxTestFirstName", firstName);
		assertEquals("WhiteBoxTestLastName", lastName);
		assertTrue(connector.deleteProfessor(testProf));
	}
}
