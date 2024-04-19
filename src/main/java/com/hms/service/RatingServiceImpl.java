package com.hms.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.hms.entity.Rating;

import com.hms.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService
{
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public Rating addRating(Rating rating) 
	{
		
		String uuid = UUID.randomUUID().toString();
		rating.setRatingId(uuid);
		return ratingRepository.save(rating);
	}

	@Override
	public Rating getRating(String ratingId)
	{
		return ratingRepository.findById(ratingId).orElseThrow(()->new IllegalArgumentException("Rating not Found !! "+ratingId));
		
	}

	@Override
	public List<Rating> getRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating updateRating(Rating rating)
	{
		Optional<Rating> ratingOptional = ratingRepository.findById(rating.getRatingId());
	    
	    if (ratingOptional.isPresent())
	    {
	        Rating presentRating = ratingOptional.get();
	        
	        presentRating.setFeedback(rating.getFeedback());
	        presentRating.setRating(rating.getRating());
//	        presentRating.setUserId(rating.getUserId());
//	        presentRating.setHotelId(rating.getHotelId());
	        
	        
	        return ratingRepository.save(presentRating);
	        
	    }
	    else
	    {
	        throw new IllegalArgumentException("No User found !! " + rating.getRatingId());
	    }
	}

	@Override
	public void deleteRating(String ratingId)
	{
		Optional<Rating> rating = ratingRepository.findById(ratingId);
		if(rating.isPresent())
		{
			ratingRepository.delete(rating.get());
			System.out.println("Raing with ID " + ratingId + " has been deleted successfully.");
		}
		else
		{
			throw new IllegalArgumentException("No Rating Found !! "+ratingId);
		}
		
	}

	@Override
	public List<Rating> getRatingsByUserId(String userId)
	{
		if(userId==null || userId.isEmpty())
		{
			 throw new IllegalArgumentException("User ID cannot be null or empty");
		}
		List<Rating> ratings = ratingRepository.findByUserId(userId);
		if(ratings.isEmpty())
		{
			 throw new RuntimeException("No ratings found for user ID: " + userId);
		}
		return ratings;
	}

	@Override
	public List<Rating> getRatingsByHotelId(String hotelId)
	{
		if(hotelId==null || hotelId.isEmpty())
		{
			 throw new IllegalArgumentException("Hotel ID cannot be null or empty");
		}
		List<Rating> ratings = ratingRepository.findByHotelId(hotelId);
		if(ratings.isEmpty())
		{
			throw new RuntimeException("No ratings found for user ID: " + hotelId);
		}
		return ratings;
		
	}

}
