package com.moviereview;

public class Review {
	
	User user;
	Movie movie;
	int reviewScore;
	
	public Review(User user, Movie movie, int reviewScore) {
		this.user = user;
		this.movie = movie;
		this.reviewScore = reviewScore;
	}

	public User getUser() {
		return user;
	}

	public Movie getMovie() {
		return movie;
	}

	public int getReviewScore() {
		return reviewScore;
	}
  
	
}
