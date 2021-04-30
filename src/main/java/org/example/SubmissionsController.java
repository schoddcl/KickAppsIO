package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.example.Controller.ButtonCell;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SubmissionsController implements Initializable {

	public static int profileID;

    @FXML
    private Button backButton;

    @FXML
    public Label profileIDLabel;

    @FXML
    private TableView<Professor> tableView;

    @FXML
    private TableColumn<Professor, String> lastName;

    @FXML
    private TableColumn<Professor, String> firstName;

    @FXML
    private TableColumn<Professor, String> rating;

    @FXML
    private TableColumn<Professor, String> college;

    @FXML
    private TableColumn<Professor, String> position;

    @FXML
    private TableColumn<Professor, String> yearsWorked;

    @FXML
    private TableColumn<Professor, String> degree;

    @FXML
    private TableColumn<Professor, String> confirm;

    @FXML
    private TableColumn<Professor, String> deny;

    @FXML
    void backButtonClicked(ActionEvent event) {
    	Parent root = null;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller controller = fxmlLoader.getController();
		controller.setProfile(profileID);
		Stage newHomepage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newHomepage.setScene(new Scene(root));
		newHomepage.show();
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

    // When comments button clicked
 	void confirmClicked(ActionEvent event, Professor prof) throws IOException {
 		//adds or denies the submission
 		DBConnector connector = new DBConnector();
		Connection conn = connector.connect();
 	}

 // When comments button clicked
  	void denyClicked(ActionEvent event, Professor prof) throws IOException {
  		//adds or denies the submission
  		DBConnector connector = new DBConnector();
 		Connection conn = connector.connect();
  	}

 // Comments button code
 	public class DenyButtonCell extends TableCell<Professor, String> {
 		final Button cellButton = new Button("Deny");

 		DenyButtonCell() {
 			cellButton.setOnAction(new EventHandler<ActionEvent>() {

 				@Override
 				public void handle(ActionEvent t) {
 					try {
 						denyClicked(t, getTableRow().getItem());
 					} catch (IOException e) {
 						e.printStackTrace();
 					}
 				}
 			});
 		}

 		// Display button if the row is not empty
 		protected void updateItem(String t, boolean empty) {
 			super.updateItem(t, empty);
 			if (!empty) {
 				setGraphic(cellButton);
 			}
 		}
 	}

 	public class ConfirmButtonCell extends TableCell<Professor, String> {
 		final Button cellButton = new Button("Confirm");

 		ConfirmButtonCell() {
 			cellButton.setOnAction(new EventHandler<ActionEvent>() {

 				@Override
 				public void handle(ActionEvent t) {
 					try {
 						confirmClicked(t, getTableRow().getItem());
 					} catch (IOException e) {
 						e.printStackTrace();
 					}
 				}
 			});
 		}

 		// Display button if the row is not empty
 		protected void updateItem(String t, boolean empty) {
 			super.updateItem(t, empty);
 			if (!empty) {
 				setGraphic(cellButton);
 			}
 		}
 	}

	public void setTable(int profileID) {

		DBConnector dbconnector = new DBConnector();
		Connection conn = dbconnector.connect();
		this.profileID = profileID;
		ResultSet rs = dbconnector.getProfileFromID(conn, profileID);
		try {
			while (rs.next()) {
				profileIDLabel.setText("Profile: " + rs.getString(2));
			}
		} catch (SQLException e) {
			profileIDLabel.setText("Profile: Default User");
		}
		// From DBConnector class connects to the database
				rs = dbconnector.getAllSubmissionsResultSet(conn);
				System.out.print(profileID);

				// Set columns of the table
				firstName.setCellValueFactory(new PropertyValueFactory<Professor, String>("firstName"));
				lastName.setCellValueFactory(new PropertyValueFactory<Professor, String>("lastName"));
				rating.setCellValueFactory(new PropertyValueFactory<Professor, String>("rating"));
				college.setCellValueFactory(new PropertyValueFactory<Professor, String>("college"));
				position.setCellValueFactory(new PropertyValueFactory<Professor, String>("position"));
				yearsWorked.setCellValueFactory(new PropertyValueFactory<Professor, String>("yearsWorked"));
				degree.setCellValueFactory(new PropertyValueFactory<Professor, String>("degree"));

				confirm.setCellValueFactory(
						new Callback<TableColumn.CellDataFeatures<Professor, String>, ObservableValue<String>>() {

							@Override
							public ObservableValue<String> call(TableColumn.CellDataFeatures<Professor, String> p) {
								return new SimpleStringProperty(p.getValue() != null, null);
							}
						});

				confirm.setCellFactory(new Callback<TableColumn<Professor, String>, TableCell<Professor, String>>() {

					@Override
					public TableCell<Professor, String> call(TableColumn<Professor, String> p) {
						// Custom cell with comments feature
						return new ConfirmButtonCell();
					}

				});

				deny.setCellValueFactory(
						new Callback<TableColumn.CellDataFeatures<Professor, String>, ObservableValue<String>>() {

							@Override
							public ObservableValue<String> call(TableColumn.CellDataFeatures<Professor, String> p) {
								return new SimpleStringProperty(p.getValue() != null, null);
							}
						});

				deny.setCellFactory(new Callback<TableColumn<Professor, String>, TableCell<Professor, String>>() {

					@Override
					public TableCell<Professor, String> call(TableColumn<Professor, String> p) {
						// Custom cell with comments feature
						return new DenyButtonCell();
					}

				});

				ObservableList<Professor> professors = dbconnector.getSubmissionsObservableList(rs);

				// Loads the data into table
				tableView.setItems(professors);
	}


}
