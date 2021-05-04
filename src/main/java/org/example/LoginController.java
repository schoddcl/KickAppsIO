package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	Stage oldStage;

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Label loginFailedLabel;

	@FXML
	private Button loginButton;

	@FXML
	void loginButtonClicked(ActionEvent event) {
		Parent root = null;
		if (checkCredentials() != -1) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
			try {
				root = fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.close();

			Stage oldWindow = oldStage;
			oldWindow.close();

			Controller controller = fxmlLoader.getController();
			Stage newHomepage = new Stage();
			newHomepage.setScene(new Scene(root));
			newHomepage.show();
			controller.setProfile(checkCredentials());

		}
	}

	int checkCredentials() {
		DBConnector connector = new DBConnector();
		Connection conn = connector.connect();
		ResultSet rs = connector.getProfileFromLogin(conn, usernameField.getText(), passwordField.getText());
		try {
			if (rs.next() == false) {
				// Login failed 
				loginFailedLabel.setText("Login Failed!");
			} else {
				// Login successful
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
