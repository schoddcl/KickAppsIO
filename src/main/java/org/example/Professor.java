package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Professor {
	// Commented out for simplicity
//	private String firstName, lastName, college, position, degree;
//	private double rating;
//	private Integer yearsWorked, profID;
//
//	public Professor(Integer profID, String firstName, String lastName, Double rating, String college, String position,
//			Integer yearsWorked, String degree) {
//		this.profID = profID;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.college = college;
//		this.position = position;
//		this.degree = degree;
//		this.rating = rating;
//		this.yearsWorked = yearsWorked;
//	}
//
//	// Getter for Prof ID
//	public Integer getProfID() {
//		return profID;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	// Getter for last name
//	public String getLastName() {
//		return lastName;
//	}
//
//	public String getCollege() {
//		return college;
//	}
//
//	public String getPosition() {
//		return position;
//	}
//
//	public String getDegree() {
//		return degree;
//	}
//
//	public double getRating() {
//		return rating;
//	}
//
//	public int getYearsWorked() {
//		return yearsWorked;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public void setCollege(String college) {
//		this.college = college;
//	}
//
//	public void setPosition(String position) {
//		this.position = position;
//	}
//
//	public void setDegree(String degree) {
//		this.degree = degree;
//	}
//
//	public void setRating(double rating) {
//		this.rating = rating;
//	}
//
//	public void setYearsWorked(int yearsWorked) {
//		this.yearsWorked = yearsWorked;
//	}
//
//	@Override
//	public String toString() {
//		return String.format(
//				"ProfessorID: %d | %s %s %.1f %s %s %d %s",
//				profID, firstName, lastName, rating, college, position, yearsWorked, degree);
//	}
	
	// Lukes changes
	private String firstName, lastName, college, position, degree, status;
	private double rating;
	private Integer yearsWorked, profID;

	public Professor(Integer profID, String firstName, String lastName, Double rating, String college, String position,
			Integer yearsWorked, String degree) {
		this.profID = profID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.college = college;
		this.position = position;
		this.degree = degree;
		this.rating = rating;
		this.yearsWorked = yearsWorked;
		this.status = "pending";
	}
	
	public Professor(Integer profID, String firstName, String lastName, Double rating, String college, String position,
			Integer yearsWorked, String degree, String status) {
		this.profID = profID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.college = college;
		this.position = position;
		this.degree = degree;
		this.rating = rating;
		this.yearsWorked = yearsWorked;
		this.status = status;
	}

	// Getter for Prof ID
	public Integer getProfID() {
		return profID;
	}

	public String getFirstName() {
		return firstName;
	}

	// Getter for last name
	public String getLastName() {
		return lastName;
	}

	public String getCollege() {
		return college;
	}

	public String getPosition() {
		return position;
	}

	public String getDegree() {
		return degree;
	}

	public double getRating() {
		return rating;
	}

	public int getYearsWorked() {
		return yearsWorked;
	}
	
	public String getStatus() {
		return status;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setYearsWorked(int yearsWorked) {
		this.yearsWorked = yearsWorked;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format(
				"ProfessorID: %d | %s %s %.1f %s %s %d %s",
				profID, firstName, lastName, rating, college, position, yearsWorked, degree);
	}

}