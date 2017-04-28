package com.tvajjala.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * OrderLineItem
 *
 * @author ThirupathiReddy V
 *
 */
@Entity
public class OrderLineItem implements Serializable {

    private static final long serialVersionUID = 8225701862845166685L;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lineItemId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private OnlineOrder onlineOrder;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    Product product;

    @Column
    private Integer quantity;

    @Column(precision = 19, scale = 4)
    private Double lineTotalPrice;

    @Column(precision = 19, scale = 4)
    private Double tax;

    public Long getLineItemId() {
	return lineItemId;
    }

    public void setLineItemId(Long lineItemId) {
	this.lineItemId = lineItemId;
    }

    public OnlineOrder getOnlineOrder() {
	return onlineOrder;
    }

    public void setOnlineOrder(OnlineOrder onlineOrder) {
	this.onlineOrder = onlineOrder;
    }

    public Product getProduct() {
	return product;
    }

    public void setProduct(Product product) {
	this.product = product;
    }

    public Integer getQuantity() {
	return quantity;
    }

    public void setQuantity(Integer quantity) {
	this.quantity = quantity;
    }

    public Double getLineTotalPrice() {
	lineTotalPrice= product.getPrice();
	return lineTotalPrice;
    }

    public Double getTax() {
	tax = BigDecimal.valueOf(product.getPrice() * quantity * .01).setScale(4, RoundingMode.HALF_UP).doubleValue();
	return tax;
    }

    @PrePersist
    public void prePersist(){
	tax=getTax();
	lineTotalPrice=getLineTotalPrice();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (product == null ? 0 : product.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final OrderLineItem other = (OrderLineItem) obj;
	if (product == null) {
	    if (other.product != null) {
		return false;
	    }
	} else if (!product.equals(other.product)) {
	    return false;
	}
	return true;
    }

}
