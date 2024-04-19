package com.hms.service;

import java.util.List;

import com.hms.entity.Rating;

public interface RatingService 
{
	public Rating addRating(Rating rating);
	public Rating getRating(String ratingId);
	public List<Rating>getRatings();
	public Rating updateRating(Rating rating);
	public void deleteRating(String ratingId);
	
	public List<Rating> getRatingsByUserId(String userId);
	public List<Rating> getRatingsByHotelId(String hotelId);
	

}
