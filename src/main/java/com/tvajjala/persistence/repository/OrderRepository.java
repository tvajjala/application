package com.tvajjala.persistence.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvajjala.persistence.model.OnlineOrder;
/**
 *
 * Online Orders Repository
 *
 * @author ThirupathiReddy V
 *
 */

@Repository
public interface OrderRepository extends JpaRepository<OnlineOrder, Serializable> {


    OnlineOrder  findByOrderNumber(String orderNumber);

    List<OnlineOrder> findByEmail(String email);
}
