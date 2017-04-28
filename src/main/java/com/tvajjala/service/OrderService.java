package com.tvajjala.service;

import static com.tvajjala.util.OrderUtil.calculatePrices;
import static com.tvajjala.util.OrderUtil.getName;
import static com.tvajjala.util.OrderUtil.getOrderNumber;
import static com.tvajjala.util.OrderUtil.getPhoneNumber;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.tvajjala.persistence.model.OnlineOrder;
import com.tvajjala.persistence.model.OrderLineItem;
import com.tvajjala.persistence.model.Product;
import com.tvajjala.persistence.repository.OrderRepository;
import com.tvajjala.persistence.repository.ProductRepository;

/**
 * Creates order service
 *
 * @author ThirupathiReddy V
 *
 */
@Service("orderService")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService {

    /** The logger instance */
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    // since it is session scoe service we can keep it as instance of this
    // class so that we can add multiple items
    OnlineOrder onlineOrder = new OnlineOrder();

    //
    {
	// Assign some order user specific data
	onlineOrder.setOrderNumber(getOrderNumber());
	onlineOrder.setPhoneNumber(getPhoneNumber());
	onlineOrder.setEmail(getName() + "@gmail.com");
    }

    public OnlineOrder placeOrder() {
	LOG.info("Creating onlineOrder {} ", onlineOrder);

	if (onlineOrder.getLineItems().isEmpty()) {
	    return null;
	}

	onlineOrder= orderRepository.save(onlineOrder);

	return onlineOrder;
    }

    public List<OnlineOrder> getAllOrders() {
	LOG.info("Returning all the orders ");


	final List<OnlineOrder> list = orderRepository.findByEmail(onlineOrder.getEmail());
	return list;
    }

    public OnlineOrder getOrder(String orderNumber) {
	LOG.info("Returning order with orderNumber {} ", orderNumber);
	return orderRepository.findByOrderNumber(orderNumber);
    }

    public OnlineOrder addToCart(String productId) {

	final Product product = productRepository.findByProductId(productId);

	if (product != null) {

	    boolean isItemExistsIntheCart = false;
	    OrderLineItem existedItem = null;
	    for (final OrderLineItem item : onlineOrder.getLineItems()) {
		if (item.getProduct().equals(product)) {
		    isItemExistsIntheCart = true;
		    existedItem = item;
		    break;
		}
	    }

	    if (isItemExistsIntheCart) {
		LOG.info("Item already exists in the cart. increasing the quantity");
		existedItem.setQuantity(existedItem.getQuantity() + 1);
		onlineOrder.getLineItems().add(existedItem);
	    } else {
		LOG.info("Adding new Item to the cart");
		final OrderLineItem lineItem = new OrderLineItem();
		lineItem.setQuantity(1);
		lineItem.setProduct(product);
		lineItem.setOnlineOrder(onlineOrder);
		onlineOrder.getLineItems().add(lineItem);
	    }

	}

	calculatePrices(onlineOrder);

	return onlineOrder;

    }

    public OnlineOrder deleteCartItem(String productId) {
	final Product product = productRepository.findByProductId(productId);

	if (product != null) {

	    OrderLineItem itemToRemove = null;
	    for (final OrderLineItem item : onlineOrder.getLineItems()) {
		if (item.getProduct().equals(product)) {
		    itemToRemove = item;
		    break;
		}
	    }

	    if (itemToRemove != null) {
		onlineOrder.getLineItems().remove(itemToRemove);
	    }

	}
	calculatePrices(onlineOrder);

	if(onlineOrder.getLineItems().isEmpty()){
	    return null;// dont return anything
	}

	return onlineOrder;
    }

}
