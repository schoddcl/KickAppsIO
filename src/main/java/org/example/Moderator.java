package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Moderator extends User {

	public Moderator(String username, String password) {
		super(username, password);
	}

	public void deleteComment(String comment, int profID) {
		String query = "Select comment WHERE profID = " + profID;
		ResultSet rs = connect(query, true);
		try {
			String currentComment = rs.getString(3);
			int startIndex = currentComment.indexOf(comment);
			currentComment = currentComment.substring(startIndex, comment.length());
			String update = "UPDATE tblComments SET comment = '" + currentComment + "'where profID = " + profID;
			connect(update, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
