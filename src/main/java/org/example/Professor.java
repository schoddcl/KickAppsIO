package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Professor {

	private String firstName, lastName, college, position, degree;
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
	}

	public Integer getProfID() {
		return profID;
	}

	public String getFirstName() {
		return firstName;
	}

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

	@Override
	public String toString() {
		return String.format(
				"ProfessorID: %d | %s %s %.1f %s %s %d %s",
				profID, firstName, lastName, rating, college, position, yearsWorked, degree);
	}

}