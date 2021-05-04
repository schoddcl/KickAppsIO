package junitTests;


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.AddController;
import org.example.DBConnector;
import org.example.Professor;
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
	void CanUpdateSubmissionsTableFromAdminSubmissionsWindow() {
		SubmissionsController submissionsController = new SubmissionsController();
		assertTrue(submissionsController.setTable(3));
	}
}
