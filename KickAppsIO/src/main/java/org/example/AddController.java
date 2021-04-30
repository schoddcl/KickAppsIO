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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController implements Initializable {
	int profileID = -1;

	// Back button on add page
	@FXML
	private Button backButton;

	@FXML
    private Button submitButton;

	@FXML
	private Label submitFailedLabel;

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

	Stage oldStage;

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

	@FXML
	void submitButtonClicked(ActionEvent event) throws IOException{
		if(		firstName.getText() != "" && lastName.getText() != "" &&
				(Double.parseDouble(rateProfScore.getText()) < 5.0 && Double.parseDouble(rateProfScore.getText()) > 0.0) &&
				Integer.parseInt(yearsWorked.getText()) > 0) {
			DBConnector connector = new DBConnector();
			Connection conn = connector.connect();
			connector.submit(conn, firstName.getText(), lastName.getText(), Double.parseDouble(rateProfScore.getText()), college.getText(), position.getText(), Integer.parseInt(yearsWorked.getText()),
			degree.getText());
		} else {
			submitFailedLabel.setText("Submit Failed!");
		}
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
