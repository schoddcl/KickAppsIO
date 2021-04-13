

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

	public Admin(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public ResultSet accessSubmissions() {
		String query = "Select * From tblAdmissions";
		ResultSet rs = connectDatabase(query, true);
		return rs;
	}
	
	public void confirmSubmission(int subID) {
		String query = "UPDATE tblAdmissions SET subType = 'true' where subID = " + subID;
		connectDatabase(query, false);
	}
	
	public void denySubmission(int subID) {
		String query = "UPDATE tblAdmissions SET subType = 'false' where subID = " + subID;
		connectDatabase(query, false);
	}
}
