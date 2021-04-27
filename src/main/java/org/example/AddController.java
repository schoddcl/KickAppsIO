package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import org.example.Controller.ButtonCell;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	
	@FXML
    private TableView<Professor> tableView;

    @FXML
    private TableColumn<Professor, String> lastNameColumn;

    @FXML
    private TableColumn<Professor, String> firstNameColumn;

    @FXML
    private TableColumn<Professor, String> ratingColumn;

    @FXML
    private TableColumn<Professor, String> collegeColumn;

    @FXML
    private TableColumn<Professor, String> positionColumn;

    @FXML
    private TableColumn<Professor, String> yearsWorkedColumn;

    @FXML
    private TableColumn<Professor, String> degreeColumn;

    @FXML
    private TableColumn<Professor, String> statusColumn;

    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// From DBConnector class connects to the database
		DBConnector dbconnector = new DBConnector();
		Connection conn = dbconnector.connect();
		ResultSet rs = dbconnector.getSubmissionsResultSet(conn, profileID);
		System.out.print(profileID);

		// Set columns of the table
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("lastName"));
		ratingColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("rating"));
		collegeColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("college"));
		positionColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("position"));
		yearsWorkedColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("yearsWorked"));
		degreeColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("degree"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("status"));

		ObservableList<Professor> professors = dbconnector.getSubmissionsObservableList(rs);

		// Loads the data into table
		tableView.setItems(professors);
	}

	@FXML
	void submitButtonClicked(ActionEvent event) throws IOException {
		if(profileID > -1) {
			if(		firstName.getText() != "" && lastName.getText() != "" &&
					(Double.parseDouble(rateProfScore.getText()) < 5.0 && Double.parseDouble(rateProfScore.getText()) > 0.0) &&
					Integer.parseInt(yearsWorked.getText()) > 0) {
				DBConnector connector = new DBConnector();
				Connection conn = connector.connect();
				connector.submit(conn, profileID, firstName.getText(), lastName.getText(), Double.parseDouble(rateProfScore.getText()), college.getText(), position.getText(), Integer.parseInt(yearsWorked.getText()),
				degree.getText());
			} else {
				submitFailedLabel.setText("Submit Failed!");
			}
		} else {
			submitFailedLabel.setText("Please go back and login");
		}
	}
}
