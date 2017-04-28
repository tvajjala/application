package com.tvajjala.persistence.repository.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tvajjala.persistence.model.Product;
import com.tvajjala.persistence.repository.ProductRepository;

@RunWith( SpringRunner.class )
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void updateProductTest() {

        Product product = new Product( "US12345678", "IPad", "New Ipad", 800.34 );

        product = productRepository.save( product );

        productRepository.updateProduct( 500.55, product.getProductId() );

        productRepository.updateProduct( 520.55, product.getProductId() );
    }

}
