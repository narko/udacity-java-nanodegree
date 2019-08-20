package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.mongo.CommentInfo;
import com.udacity.course3.reviews.entity.mongo.ReviewInfo;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewInfoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // Wire needed JPA repositories here
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewInfoRepository reviewInfoRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (!reviewOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            comment.setReview(reviewOptional.get());
            commentRepository.save(comment);

            Optional<ReviewInfo> reviewInfoOptional = reviewInfoRepository.findById(reviewId.toString());
            if (reviewInfoOptional.isPresent()) {
                ReviewInfo reviewInfo = reviewInfoOptional.get();
                reviewInfo.addComment(new CommentInfo(comment));
                reviewInfoRepository.save(reviewInfo);
            }

            return ResponseEntity.ok().build();
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        return ResponseEntity.ok(commentRepository.findAllByReviewId(reviewId));
    }
}