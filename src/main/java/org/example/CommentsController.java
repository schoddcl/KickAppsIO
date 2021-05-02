package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CommentsController implements Initializable {

	int profileID = -1;

	Professor prof = null;

	@FXML
	private ContextMenu deleteComment;
	
	@FXML
	private Label commentFailedLabel;
	
	@FXML
	private TextArea commentID;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button commentButton;
	
	@FXML
	private TableView<Comment> tableView;
	
	@FXML
	private TableColumn<Comment, String> professorName;

	private static Scene scene;

	@FXML
	void backButtonClicked(ActionEvent event) throws IOException {
		Parent root = null;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller controller = fxmlLoader.getController();
		controller.setProfile(this.profileID);
		Stage newHomepage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newHomepage.setScene(new Scene(root));
		newHomepage.show();
	}
	
	@FXML
	void commentButtonClicked(ActionEvent event) {
		try {
			DBConnector connector = new DBConnector();
			Connection conn = connector.connect();
			if(profileID > 0) {
				String query = "INSERT INTO tblComments (profileID, profID, comment) VALUES(" + profileID + "," + prof.getProfID() + 
						",'"+ commentID.getText() + "')";
				connector.executeUpdate(query);
			}
			else
				commentFailedLabel.setText("Must Login to Submit a Comment");
		}catch (Exception e) {
				e.printStackTrace();
		}
		setProfComments(this.prof);
		commentID.clear();
	}
	
	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (profileID != -1) {
		deleteComment.getItems().add(new MenuItem("" + profileID));
		}
	}

	public void setProfComments(Professor passProf) {
		DBConnector connection = new DBConnector();
		prof = passProf;
		ResultSet commentsForProf = connection.getProfComments(connection.connect(), passProf.getProfID());
		ResultSet query = connection.getProfessorResultSet(connection.connect());
		professorName.setText(prof.getLastName() + ", " + prof.getFirstName());
		ObservableList<Comment> comments = connection.getProfessorsCommentsList(commentsForProf);
		professorName.setCellValueFactory(new PropertyValueFactory<Comment, String>("comment"));
		tableView.setItems(comments);
	}
	
	public void setProfile(int profileID) {
		DBConnector connection = new DBConnector();
		Connection conn = connection.connect();
		ResultSet rs = connection.getProfileFromID(conn, profileID);
		try {
			while (rs.next()) {
				deleteComment.getItems().add(new MenuItem("Delete Comment"));
				this.profileID = profileID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void deleteCommentClicked() {
		DBConnector connection = new DBConnector();
		int commentID = tableView.getSelectionModel().getSelectedItem().getCommentID();
		connection.executeQuery("DELETE FROM tblComments WHERE commentID = "+ commentID, false);
		setProfComments(prof);
	}
	
	
}
