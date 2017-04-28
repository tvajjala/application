package com.tvajjala.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * For this demo i'm not creating catalogs/ SKU etc
 *
 * @author ThirupathiReddy V
 */

import com.tvajjala.persistence.model.Product;
import com.tvajjala.persistence.repository.ProductRepository;

@Service( "productService" )
public class ProductService {

    /** The logger instance */
    private static final Logger LOG = LoggerFactory.getLogger( ProductService.class );

    @Autowired
    ProductRepository productRepository;

    public Product createProduct( Product product ) {
        LOG.info( "Creating product {} ", product );
        return productRepository.save( product );
    }

    public List<Product> getAllProducts() {
        LOG.info( "Returning all the products " );
        return productRepository.findAll();
    }

    public Product getProduct( String productId ) {
        LOG.info( "Return produt with productId {} ", productId );
        return productRepository.findByProductId( productId );
    }

}
