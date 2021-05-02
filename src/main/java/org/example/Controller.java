package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Node;
//import jdk.internal.icu.text.UnicodeSet;

public class Controller implements Initializable {
	// Profile, set as -1 to start
	private int profileID = -1;
	private String permission = "None";
	
	// SubmissionsButton
	@FXML
	private Button submissionsButton;
	@FXML
	void submissionsButtonClicked(ActionEvent event) throws IOException {
		// Changes the page to an add page
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("submissions.fxml"));
			Parent root = fxmlLoader.load();
			SubmissionsController SubmissionsController = fxmlLoader.getController();
			SubmissionsController.profileIDLabel.setText("Profile: Default User");
			SubmissionsController.setTable(profileID);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		}
			
			

	// Login Button
	@FXML
	private Button loginButton;

	// Runs when login button is clicked
	Stage loginStage;

	@FXML
	void loginButtonClicked(ActionEvent event) throws IOException {

		if (loginButton.getText().equals("Logout")) {
			profileID = -1;
			Parent root = null;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
			try {
				root = fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.close();

			Controller controller = fxmlLoader.getController();
			Stage newHomepage = new Stage();
			newHomepage.setScene(new Scene(root));
			newHomepage.show();
		} else {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = fxmlLoader.load();

			// Pass in old stage value so it can be changed when logging in
			LoginController loginController = fxmlLoader.getController();
			loginController.oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			Stage loginStage = new Stage();
			loginStage.setScene(new Scene(root));
			loginStage.show();
		}
	}

	// Add Button
	@FXML
	private Button addButton;

	// Runs when add button is clicked
	@FXML
	void addButtonClicked(ActionEvent event) throws IOException {
		// Changes the page to an add page
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add.fxml"));
		Parent root = fxmlLoader.load();
		AddController addController = fxmlLoader.getController();
		addController.profileIDLabel.setText("Profile: Default User");
		addController.setTable(profileID);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	// Search Text Field
	@FXML
	private TextField searchField;

	@FXML
	private Label profileLabel;

	public void setProfile(int profileID) {
		DBConnector connection = new DBConnector();
		Connection conn = connection.connect();
		ResultSet rs = connection.getProfileFromID(conn, profileID);
		
		try {
			while (rs.next()) {
				profileLabel.setText("Profile: " + rs.getString(2));
				loginButton.setText("Logout");
				this.profileID = profileID;
				this.permission = rs.getString(4);
				if(permission.equals("Admin") || permission.equals("Moderator"))
				submissionsButton.setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Table
	@FXML
	private TableView<Professor> tableView;

	// Table Columns
	@FXML
	private TableColumn<Professor, String> firstName;

	@FXML
	private TableColumn<Professor, String> lastName;

	@FXML
	private TableColumn<Professor, Double> rating;

	@FXML
	private TableColumn<Professor, String> college;

	@FXML
	private TableColumn<Professor, String> position;

	@FXML
	private TableColumn<Professor, Integer> yearsWorked;

	@FXML
	private TableColumn<Professor, String> degree;

	@FXML
	private TableColumn<Professor, String> comments;

	// Comments button code
	public class ButtonCell extends TableCell<Professor, String> {
		final Button cellButton = new Button("Comments");

		ButtonCell() {
			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					try {
						commentsClicked(t, getTableRow().getItem());
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

	// When comments button clicked
	void commentsClicked(ActionEvent event, Professor prof) throws IOException {
		// Changes the page to an add page
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("comments.fxml"));
		Parent root = fxmlLoader.load();

		CommentsController commentsController = fxmlLoader.getController();
		commentsController.setProfComments(prof);
		commentsController.setProfile(this.profileID);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		stage.setScene(new Scene(root));
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// From DBConnector class connects to the database
		DBConnector dbconnector = new DBConnector();
		Connection conn = dbconnector.connect();
		ResultSet rs = dbconnector.getProfessorResultSet(conn);

		// Set columns of the table
		firstName.setCellValueFactory(new PropertyValueFactory<Professor, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<Professor, String>("lastName"));
		rating.setCellValueFactory(new PropertyValueFactory<Professor, Double>("rating"));
		college.setCellValueFactory(new PropertyValueFactory<Professor, String>("college"));
		position.setCellValueFactory(new PropertyValueFactory<Professor, String>("position"));
		yearsWorked.setCellValueFactory(new PropertyValueFactory<Professor, Integer>("yearsWorked"));
		degree.setCellValueFactory(new PropertyValueFactory<Professor, String>("degree"));

		comments.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Professor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Professor, String> p) {
						return new SimpleStringProperty(p.getValue() != null, null);
					}
				});

		comments.setCellFactory(new Callback<TableColumn<Professor, String>, TableCell<Professor, String>>() {

			@Override
			public TableCell<Professor, String> call(TableColumn<Professor, String> p) {
				// Custom cell with comments feature
				return new ButtonCell();
			}

		});

		ObservableList<Professor> professors = dbconnector.getProfessorsObservableList(rs);

		// Loads the data into table
		tableView.setItems(professors);

		// This wraps the observable list in the table into a filtered list so we can
		// use the search function
		FilteredList<Professor> filteredProfessors = new FilteredList<>(professors);

		// Adds a listener to the search box that checks what the user is typing in
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredProfessors.setPredicate((professor -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (professor.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) { // By first name
					return true;
				} else if (professor.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) { // By last name
					return true;
				} else if (professor.getCollege().toLowerCase().indexOf(lowerCaseFilter) != -1) { // By college
					return true;
				} else if (professor.getDegree().toLowerCase().indexOf(lowerCaseFilter) != -1) { // By degree
					return true;
				} else if (professor.getPosition().toLowerCase().indexOf(lowerCaseFilter) != -1) { // By position
					return true;
				} else if (String.valueOf(professor.getYearsWorked()).indexOf(lowerCaseFilter) != -1) { // By years
																										// worked
					return true;
				} else if (String.valueOf(professor.getRating()).indexOf(lowerCaseFilter) != -1) { // By rating
					return true;
				} else {
					return false;
				}
			}));
		});

		// Items searched for are set to be shown in the table
		SortedList<Professor> sortedProfessors = new SortedList<>(filteredProfessors);
		sortedProfessors.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedProfessors);
	}
}
