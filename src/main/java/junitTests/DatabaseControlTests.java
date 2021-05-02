package junitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import org.example.Admin;
import org.example.DBConnector;
import org.junit.jupiter.api.Test;

public class DatabaseControlTests {
	public boolean checkForDuplicates(String table, String[] columns) throws SQLException {
		DBConnector dbc = new DBConnector();
		ResultSet rs = dbc.executeQuery("Select * From " + table, true);
		int size = getSize(table);
		String[] loopable= new String[size];
		for(int i=0; rs.next(); i++)
			for(String s: columns) {
				if(loopable[i]!=null)
					loopable[i] = loopable[i] + rs.getString(rs.findColumn(s));
				else
					loopable[i] = rs.getString(rs.findColumn(s));
			}
		for(int i=0; i < size; i++)
			for(int j=size-1; j >= 0; j--)
				if(loopable[i].equals(loopable[j]) && i!=j) {
					return true;
				}
		return false;
		
	}
	
	public int getSize(String table) throws SQLException {
		DBConnector dbc = new DBConnector();
		ResultSet rs = dbc.executeQuery("SELECT COUNT(*) as Count FROM "+ table, true);
		while(rs.next())
			return rs.getInt(1);
		return -1;
		
	}
	
	@Test
	public void professorDupes() throws SQLException {
		String[] cols = new String[]{"firstName","lastName","college"};
		boolean flag = checkForDuplicates("tblProfessors", cols);
		assertEquals(false, flag);
	}
	
	@Test
	public void profileDupes() throws SQLException {
		String[] cols = new String[]{"username"};
		boolean flag = checkForDuplicates("tblProfiles", cols);
		assertEquals(false, flag);
	}
	
	@Test 
	public void tableCheck() throws SQLException {
		DBConnector dbc = new DBConnector();
		ResultSet rs = dbc.executeQuery("SELECT * FROM information_schema.tables WHERE  TABLE_TYPE = 'BASE TABLE'", true);
		String[] tableNames = new String[]{"tblAdmissions","tblComments", "tblProfiles","tblProfessors","tblClasses"};
		for(int i=0;rs.next(); i++) 
			assertEquals(tableNames[i], rs.getString(3));
		
		}
}
