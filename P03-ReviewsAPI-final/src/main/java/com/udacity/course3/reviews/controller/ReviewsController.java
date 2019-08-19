package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.mongo.ReviewInfo;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewInfoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // Wire JPA repositories here
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewInfoRepository reviewInfoRepository;
    @Autowired
    ProductRepository productRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {
        Optional<Product> optional = productRepository.findById(productId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            // save data JPA
            review.setProduct(optional.get());
            reviewRepository.save(review);

            // save data in MongoDb
            ReviewInfo reviewInfo = new ReviewInfo(review);
            reviewInfoRepository.save(reviewInfo);

            return ResponseEntity.ok().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            List<Integer> reviewIds = reviewRepository.findReviewIdsByProductId(productId);
            List<String> stringIds = new ArrayList<>(reviewIds.size());
            reviewIds.forEach(id -> stringIds.add(id.toString()));
            List<ReviewInfo> reviews = StreamSupport.stream(
                    reviewInfoRepository.findAllById(stringIds).spliterator(), false).collect(Collectors.toList());

            return ResponseEntity.ok(reviews);
        }
    }
}