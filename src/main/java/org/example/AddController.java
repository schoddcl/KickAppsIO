package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController implements Initializable {
	int profileID = -1;

	// Back button on add page
	@FXML
	private Button backButton;
	
	@FXML
    private TextField lastName;

    @FXML
    private TextField firstName;

    @FXML
    private TextField rateProfScore;

    @FXML
    private TextField college;

    @FXML
    private TextField position;

    @FXML
    private TextField yearsWorked;

    @FXML
    private TextField degree;
    
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	void submitButtonClicked(ActionEvent event) throws IOException{
		// if first/last == "" throw error
		// if rating >5 or <0 throw error
		// if years worked <0 throw error
		// else allObjects = last, first, ....
		// query string + allObject;
		// insert query into 
		/**
		 * DBConnector connector = new DBConnector();
		Connection conn = connector.connect();
		ResultSet rs = connector.getProfileFromLogin(conn, usernameField.getText(), passwordField.getText());
		 */
	}

}
