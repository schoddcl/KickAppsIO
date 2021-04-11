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

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Node;
//import jdk.internal.icu.text.UnicodeSet;

public class Controller implements Initializable {

	// Login Button
	@FXML
	private Button loginButton;

	// Runs when login button is clicked
	@FXML
	void loginButtonClicked(ActionEvent event) throws IOException {
		// Loads the login popup window
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
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
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		;
		stage.setScene(new Scene(root));
		stage.show();
	}

	// Search Text Field
	@FXML
	private TextField searchField;

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

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		;
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
				// Custon cell with comments feature
				return new ButtonCell();
			}

		});

//		ObservableList<Professor> professors = FXCollections.observableArrayList();
		ObservableList<Professor> professors = dbconnector.getProfessorsObservableList(rs);
		// This adds the data to Professor objects and accounts for the fact that the
		// degree area is null sometimes
//		try {
//			while (rs.next()) {
//				if (rs.getString(9) == null) {
//					professors.add(new Professor(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(6),
//							rs.getString(7), "", rs.getDouble(5), rs.getInt(8)));
//				} else {
//					professors.add(new Professor(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(6),
//							rs.getString(7), rs.getString(9), rs.getDouble(5), rs.getInt(8)));
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
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
