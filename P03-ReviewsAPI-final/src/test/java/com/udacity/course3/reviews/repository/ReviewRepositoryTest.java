package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testFindReviewIdsByProductId() {
        Product product = new Product();
        product.setName("Product_title");
        product.setDescription("Product_desc");

        entityManager.persist(product);
        entityManager.flush();

        Review review1 = new Review();
        review1.setTitle("Review_title");
        review1.setProduct(product);

        entityManager.persist(review1);
        entityManager.flush();

        assertThat(product.getId()).isNotNull();
        assertThat(review1.getId()).isNotNull();

        Review review2 = new Review();
        review2.setTitle("Review_title");
        review2.setProduct(product);

        entityManager.persist(review2);
        entityManager.flush();

        assertThat(review2.getId()).isNotNull();

        List<Integer> ids = reviewRepository.findReviewIdsByProductId(product.getId());
        assertThat(ids).isNotEmpty();
        assertTrue(ids.containsAll(Arrays.asList(review1.getId(), review2.getId())));
    }
}
