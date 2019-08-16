package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired private ProductRepository repository;
    @Autowired private EntityManager entityManager;

    @Test
    public void testFindByProductId() {
        Product product = new Product();

        product.setName("Nexus 5X");
        product.setDescription("New Google 5.2 inches Android phone");

        entityManager.persist(product);
        entityManager.flush();

        Optional<Product> optional = repository.findById(product.getId());
        assertThat(optional).isPresent();
        assertEquals(optional.get().getId(), product.getId());
    }
}
