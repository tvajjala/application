package com.tvajjala.persistence.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tvajjala.persistence.model.Product;

/**
 * Product Repository
 *
 * @author ThirupathiReddy V
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Serializable> {

    Product findByProductId( String productId );

    @Modifying // This is important to use while updating
    @Query( "UPDATE Product p SET p.price=?1 WHERE p.productId=?2" )
    void updateProduct( Double price, String productId );

}
