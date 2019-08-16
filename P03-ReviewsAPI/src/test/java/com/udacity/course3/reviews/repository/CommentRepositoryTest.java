package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
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
public class CommentRepositoryTest {
    @Autowired private CommentRepository commentRepository;
    @Autowired private EntityManager entityManager;

    @Test
    public void testFindAllByReviewId() {
        Product product = new Product();
        product.setName("Product A");

        entityManager.persist(product);
        entityManager.flush();

        Review review = new Review();
        review.setTitle("Review 1");
        review.setProduct(product);

        entityManager.persist(review);
        entityManager.flush();

        Comment comment = new Comment();
        comment.setTitle("Comment A");
        comment.setReview(review);

        entityManager.persist(comment);
        entityManager.flush();

        assertThat(product.getId()).isNotNull();
        assertThat(review.getId()).isNotNull();
        assertThat(comment.getId()).isNotNull();

        List<Comment> comments = commentRepository.findAllByReviewId(review.getId());
        assertThat(comments).isNotNull();
        assertThat(comments).contains(comment);
        assertEquals(comment.getReview().getId(), review.getId());
    }
}
