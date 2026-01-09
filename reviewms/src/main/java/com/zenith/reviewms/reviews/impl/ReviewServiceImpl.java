package com.zenith.reviewms.reviews.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenith.reviewms.reviews.Review;
import com.zenith.reviewms.reviews.ReviewRepository;
import com.zenith.reviewms.reviews.ReviewService;


@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviewsByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        // if (review.getCompany() != null && review.getCompany().getId().equals(companyId)) {
        //     reviewRepository.save(review);
        //     return true;
        // }
        // return false;
       
        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);
        if (existingReview != null) {
            existingReview.setTitle(updatedReview.getTitle());
            existingReview.setDescription(updatedReview.getDescription());
            existingReview.setRating(updatedReview.getRating());
            existingReview.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(existingReview);
            return true;
        }
        return false;
    
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);
        if (existingReview != null) {
            reviewRepository.delete(existingReview);
            return true;
        }
        return false;
    
    }
    
}
