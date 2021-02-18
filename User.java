package com.moviereview;


public class User {
	private String userName;
	private UserType userType;
	private int countReviews;
	
	public User(String userName, UserType userType, int countReviews) {
		this.userName = userName;
		this.userType = userType;
		this.countReviews = countReviews;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public int getCountReviews() {
		return countReviews;
	}
	
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public UserType getUserType() {
		return userType;
	}
	public void setCountReviews(int countReview) {
		this.countReviews = countReview;
	}


}
