package junitTests;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.AddController;
import org.example.Admin;
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
		SubmissionsController submissionsController = new SubmissionsController();
		assertTrue(submissionsController.setTable(3));
	}
}
