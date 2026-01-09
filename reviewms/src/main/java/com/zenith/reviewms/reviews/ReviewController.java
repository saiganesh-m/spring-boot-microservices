package com.zenith.reviewms.reviews;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zenith.reviewms.reviews.messaging.ReviewMessageProducer;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviewsByCompanyId(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAllReviewsByCompanyId(companyId), HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        boolean isAdded = reviewService.addReview(companyId, review);
        if (isAdded) {
            reviewMessageProducer.sendReviewMessage(review);
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add review", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        boolean isUpdated = reviewService.updateReview(reviewId, review);
        if (isUpdated) {
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update review", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if (isDeleted) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete review", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/average-rating")
    public Double getAverageRating(Long companyId) {
        List<Review> reviews = reviewService.getAllReviewsByCompanyId(companyId);
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
