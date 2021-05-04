package junitTests;


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.AddController;
import org.example.DBConnector;
import org.example.Professor;
import org.example.Profile;
import org.example.SubmissionsController;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

class IntegrationTests {

	@Test
	void CanGetAProfessorByIDFromDataBase() {
		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
 		SubmissionsController submissionsController = new SubmissionsController();
 		ResultSet rs = submissionsController.getAllPendingSubmissions();
 		assertTrue(connector.getProfessorsObservableList(rs) instanceof ObservableList<?>);
	}

	@Test
	void CanDeleteProfessorFromDatabase() {
		Professor testProf = new Professor(-1, "WhiteBoxTestFName", "WhiteBoxTestLName", 1.0, "WhiteBoxTest", "WhiteBoxTest", 1, "WhiteBoxTest");
		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
		connector.deleteProfessorByFirstAndLastName(testProf);
		assertTrue(connector.deleteProfessorByFirstAndLastName(testProf));
	}

	@Test
	void CanGetSubmissionsForAGivenProfile() {
		Profile tester = new Profile("sarrazlt", "cse201", "admin", 3);
		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
		assertTrue(connector.getSubmissionsResultSet(conn, tester.getProfileID()) instanceof ResultSet);
	}
}
