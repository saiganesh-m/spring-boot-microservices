package com.zenith.reviewms.reviews;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewsByCompanyId(Long companyId);
    Review getReviewById(Long reviewId);
    boolean addReview(Long companyId, Review review);
    boolean updateReview(Long reviewId, Review review);
    boolean deleteReview(Long reviewId);
}
