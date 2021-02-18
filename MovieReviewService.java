package com.moviereview;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MovieReviewService {

	List<Movie> movies = new ArrayList<>();
	List<User> users = new ArrayList<>();
	List<Review> reviews = new ArrayList<>();
	
	Map<String,User> userNameToUserMap = new HashMap<>();
	Map<String,Movie> movieNameToMovieMap = new HashMap<>();
	
	// Adds user to the users list and keep a mapping of user name and user object into userNameToUserMap
	public User addUser(String userName) {
		
		User user = new User(userName, UserType.Viewer, 0);
		users.add(user);
		userNameToUserMap.put(userName, user);
		return user;
		
	}
	
	// Adds movie to the movies list and keep a mapping of movie name and movie object into movieNameToMovieMap
	public Movie addMovie(String movieName, int yearOfRelease , List<Genere> generes) {
		
		Movie movie = new Movie(movieName, yearOfRelease, generes);
		movies.add(movie);
		movieNameToMovieMap.put(movieName, movie);
		return movie;
	}
	
	
	public Review addReview(String userName, String movieName, int score) throws Exception{
		if(!movieNameToMovieMap.containsKey(movieName)) {
			throw new Exception("Exception movie yet to be released");
		}
		User user = userNameToUserMap.get(userName);
		Movie movie = movieNameToMovieMap.get(movieName);
		
	   if(user.getCountReviews()>3) score = score*2;	
	   
	   Review review = new Review(user, movie , score);
	   if( isMultipleReviews(review, reviews)) {
		   throw new Exception("Exception multiple reviews not allowed");
	   }
	   reviews.add(review);
	   user.setCountReviews(user.getCountReviews()+1);
	   
	   promoteUser(user);
	   return review;
	}
	//Checks if user has multiple reviews for the same movie
	public boolean isMultipleReviews(Review reviewIn, List<Review> reviews) {
		for(Review review: reviews) {
			if(review.getUser().getUserName().equals(reviewIn.getUser().getUserName())  && review.getMovie().getMovieName().equals(reviewIn.getMovie().getMovieName()))
				return true;
		}
		return false;
	}
	
	//promotes a user to critics if user review count is greater than 3
	public void promoteUser(User user) {
		if(user.getCountReviews()>3) {
			user.setUserType(UserType.Critics);
		}
	}
	//calculate average review score for a given year
	public double getAverageReviewScoreByYear(int year) {
		
		 double averageScoreSum = 0.0;
		 int averageScoreCount=0;
		 for(Review review: reviews) {
			 if(review.getMovie().getReleasedYear()==year) {
				 averageScoreSum+= review.getReviewScore();
				 averageScoreCount= averageScoreCount+1;
			 }
			 
		 }
		 double averageScore = averageScoreSum/averageScoreCount;
		 return averageScore;
		
	}
	//calculate average review score for a given movie
	public double getAverageReviewScoreByMovie(String movie) {
		
		double averageScoreSum = 0.0;
		 int averageScoreCount=0;
		 for(Review review: reviews) {
			 if(review.getMovie().getMovieName().equals(movie)) {
				 averageScoreSum+= review.getReviewScore();
				 averageScoreCount= averageScoreCount+1;
			 }
			 
		 }
		 double averageScore = averageScoreSum/averageScoreCount;
		 return averageScore;
	}
	// fetch top n movies with top total review score
	public List<Movie> getTopMoviesByReviewScoreAndGenere(int n , Genere genere ){
		
		List<Movie> result = new ArrayList<>();
		//moviename as key , value as sum of review score of the movie
		Map<String,Integer> movieToTotalScoreMap = new HashMap<>();
		for(Review review: reviews) {
			if(review.getMovie().getMovieGeneres().contains(genere)) {
				if(!movieToTotalScoreMap.containsKey(review.getMovie().getMovieName())){
					movieToTotalScoreMap.put(review.getMovie().getMovieName(), review.getReviewScore());
				}
				else {
					movieToTotalScoreMap.put(review.getMovie().getMovieName(), review.getReviewScore() + movieToTotalScoreMap.get(review.getMovie().getMovieName()));
				}
			}
		}
		
		Comparator<Map.Entry<String,Integer>> cmp = new Comparator<Map.Entry<String,Integer>>() {
			public int compare(Map.Entry<String,Integer> a, Map.Entry<String,Integer> b) {
				return a.getValue()-b.getValue();
			}
		};
		
		//PriorityQueue to store map entries  with bottom total review score of movies, minimum total review score will be at root
		PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<>(cmp);
		for(Map.Entry<String, Integer> e: movieToTotalScoreMap.entrySet()) {
			pq.add(e);
			if(pq.size()>n) {
				pq.remove();
			}
		}
		//after above operation, pq will be having top n total review scores left
		
		while(!pq.isEmpty()) {
			Map.Entry<String,Integer> entry =  pq.remove();
			Movie movie = movieNameToMovieMap.get(entry.getKey());
			result.add(movie);
			
		}
		return result;
		
	}
	
	

}
