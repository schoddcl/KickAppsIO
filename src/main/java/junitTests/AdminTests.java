package junitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.Admin;
import org.example.DBConnector;
import org.example.Professor;
import org.junit.jupiter.api.Test;

class AdminTests {

	@Test
	void canCallAccessSubmissions() {
		String result = "";
		Admin a = new Admin("Luke", "Sarrazine", "Admin");
		assertTrue(a.accessSubmissions() instanceof ResultSet);
	}

	@Test
	void canCallConfirmSubmission() {
		Professor testProf = new Professor(-1, "WhiteBoxTestFName", "WhiteBoxTestLName", 1.0, "WhiteBoxTest", "WhiteBoxTest", 1, "WhiteBoxTest");
		Admin a = new TestAdmin("Luke", "Sarrazine", "Admin");
		assertTrue(a.confirmSubmission(testProf));
	}

	@Test
	void canCallDenySubmission() {
		Professor testProf = new Professor(-1, "WhiteBoxTestFName", "WhiteBoxTestLName", 1.0, "WhiteBoxTest", "WhiteBoxTest", 1, "WhiteBoxTest");
		Admin a = new TestAdmin("Luke", "Sarrazine", "Admin");
		assertTrue(a.denySubmission(testProf));
	}
}

class TestAdmin extends Admin {

	public TestAdmin(String username, String password, String permission) {
		super(username, password, permission);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean confirmSubmission(Professor prof) {
		return true;
	}

	@Override
	public boolean denySubmission(Professor prof) {
		return true;
	}
}
