package com.hms.conroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.entity.Rating;

import com.hms.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController
{
	@Autowired
	private RatingService ratingService;
	
	@PostMapping
	public ResponseEntity<String> addRating(@RequestBody Rating rating) {
	    Rating addedRating = ratingService.addRating(rating);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body("Rating with ID " + addedRating.getRatingId() + " has been created successfully!");
	}
	
	@GetMapping("/{ratingId}")
	public ResponseEntity<Rating> getRating(@PathVariable String ratingId)
	{
		Rating rating = ratingService.getRating(ratingId);
		return ResponseEntity.status(HttpStatus.OK).body(rating);
	}
	@GetMapping
	public ResponseEntity<List<Rating>> getRatings()
	{
		List<Rating> ratings = ratingService.getRatings();
		return ResponseEntity.status(HttpStatus.OK).body(ratings);
	}
	
	@DeleteMapping("/{ratingId}")
	public ResponseEntity<String> deleteRating(@PathVariable String ratingId)
	{
		try 
		{
			ratingService.deleteRating(ratingId);
			  return ResponseEntity.status(HttpStatus.OK).body("Rating with ID " + ratingId + " has been deleted successfully.");
	    } catch (IllegalArgumentException ex) {
	        
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating with ID " + ratingId + " not found.");
	    }
	}
	@PutMapping("/{ratingId}")
	public ResponseEntity<String> updateRating(@PathVariable String ratingId,@RequestBody Rating rating)
	{
		if (!ratingId.equals(rating.getRatingId()))
	    {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID in the path does not match the user ID in the request body.");
	    }
	    
	    try 
	    {
	        Rating updatedRating = ratingService.updateRating(rating);
	        String message = "Rating with ID " + ratingId + " has been updated successfully.";
	        return ResponseEntity.status(HttpStatus.OK).body(message);
	    }
	    catch (IllegalArgumentException ex)
	    {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found with ID: " + ratingId);
	    } 
	    catch (Exception ex)
	    {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the rating.");
	    }
	}
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId)
	{
		List<Rating> ratingsByUserId = ratingService.getRatingsByUserId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(ratingsByUserId);
	}
	
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId)
	{
		List<Rating> ratingsByHotelId = ratingService.getRatingsByHotelId(hotelId);
		
		return ResponseEntity.status(HttpStatus.OK).body(ratingsByHotelId);
	}

}
