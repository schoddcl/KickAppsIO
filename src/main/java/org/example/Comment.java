package org.example;

public class Comment {
	
	private int commentID, profileID, profID;
	private String comment;
	
	public Comment(int commentID, int profileID, int profID, String comment) {
		setCommentID(commentID);
		setProfileID(profileID);
		setProfID(profID);
		setComment(comment);
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getProfileID() {
		return profileID;
	}

	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}

	public int getProfID() {
		return profID;
	}

	public void setProfID(int profID) {
		this.profID = profID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
