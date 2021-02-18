package com.moviereview;

import java.util.List;

public class Movie {

	private String movieName;
	private int releasedYear;
	private List<Genere> movieGeneres;
	
	public Movie(String movieName, int releasedYear, List<Genere> movieGeneres) {
		this.movieName = movieName;
		this.releasedYear = releasedYear;
		this.movieGeneres = movieGeneres;
	}

	public String getMovieName() {
		return movieName;
	}

	public int getReleasedYear() {
		return releasedYear;
	}

	public List<Genere> getMovieGeneres() {
		return movieGeneres;
	}
	
	
	
}
