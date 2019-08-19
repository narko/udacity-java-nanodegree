package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.mongo.ReviewInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewInfoRepositoryTest {
    @Autowired private ReviewInfoRepository reviewInfoRepository;

    @Test
    public void testPersistance() {
        Product product = new Product();
        product.setName("Nexus 5X");
        product.setDescription("New Google 5.2 inches Android phone");

        Review review = new Review();
        review.setTitle("Better than expected");
        review.setText("Really good.");
        review.setProduct(product);

        Comment comment1 = new Comment();
        comment1.setTitle("Definitely recommendable");
        comment1.setReview(review);

        ReviewInfo reviewInfo = new ReviewInfo(review);
        reviewInfo.addComment(comment1);

        ReviewInfo savedReviewInfo = reviewInfoRepository.save(reviewInfo);
        assertEquals(reviewInfo.getTitle(), savedReviewInfo.getTitle());
        assertEquals(reviewInfo.getProduct(), savedReviewInfo.getProduct());
        assertEquals(reviewInfo.getComments(), savedReviewInfo.getComments());

        List reviews = reviewInfoRepository.findAll();
        assertTrue(!reviews.isEmpty());
        ReviewInfo retrievedReviewInfo = (ReviewInfo) reviews.get(0);
        assertEquals(reviewInfo.getId(), retrievedReviewInfo.getId());
        assertEquals(reviewInfo.getTitle(), retrievedReviewInfo.getTitle());
        assertEquals(reviewInfo.getText(), retrievedReviewInfo.getText());
    }
}
