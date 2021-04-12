package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddController implements Initializable {
	int profileID = -1;

	// Back button on add page
	@FXML
	private Button backButton;

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

}
