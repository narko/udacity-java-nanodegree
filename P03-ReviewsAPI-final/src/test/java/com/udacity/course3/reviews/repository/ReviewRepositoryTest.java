package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryTest {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private EntityManager entityManager;

    @Test
    public void testFindReviewsByProductId() {
        Product product = new Product();
        product.setName("Product_title");
        product.setDescription("Product_desc");

        entityManager.persist(product);
        entityManager.flush();

        Review review = new Review();
        review.setTitle("Review_title");
        review.setProduct(product);

        entityManager.persist(review);
        entityManager.flush();

        assertThat(product.getId()).isNotNull();
        assertThat(review.getId()).isNotNull();

        List<Review> reviews = reviewRepository.findReviewsByProductId(product.getId());
        assertThat(reviews).isNotEmpty();
        assertThat(reviews).contains(review);
        Review returnedReview = reviews.get(reviews.indexOf(review));
        assertEquals(returnedReview.getProduct().getId(), product.getId());
    }
}
