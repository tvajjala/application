package com.tvajjala.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * H2 Database not supporting Order as table name .hence i used OnlineOrder
 *
 * @author ThirupathiReddy V
 *
 */
@Entity
@Table(name="onlineOrder")
public class OnlineOrder implements Serializable {

    private static final long serialVersionUID = -2078694241706530544L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long orderId;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @Column(precision = 19, scale = 4)
    private Double tax;

    @Column(precision = 19, scale = 4)
    private Double discountValue=new Double(0.00);

    @Column(precision = 19, scale = 4)
    private Double shippingCost=new Double(5.00);

    @Column(precision = 19, scale = 4)
    private Double subTotal;

    @Column(precision = 19, scale = 4)
    private Double additionalFee=new Double(1.00);

    @Column(precision = 19, scale = 4)
    private Double totalPrice;

    @Column(length = 255)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    @OneToMany(mappedBy="onlineOrder",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<OrderLineItem> lineItems = new HashSet<>();

    public Set<OrderLineItem> getLineItems() {
	return lineItems;
    }

    public void setLineItems(Set<OrderLineItem> lineItems) {
	this.lineItems = lineItems;
    }

    public Long getOrderId() {
	return orderId;
    }

    public void setOrderId(Long orderId) {
	this.orderId = orderId;
    }

    public String getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
	this.orderNumber = orderNumber;
    }

    public Double getTax() {
	return tax;
    }

    public void setTax(Double tax) {
	this.tax = tax;
    }

    public Double getDiscountValue() {
	return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
	this.discountValue = discountValue;
    }

    public Double getShippingCost() {
	return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
	this.shippingCost = shippingCost;
    }

    public Double getSubTotal() {
	return subTotal;
    }

    public void setSubTotal(Double subTotal) {
	this.subTotal = subTotal;
    }

    public Double getAdditionalFee() {
	return additionalFee;
    }

    public void setAdditionalFee(Double additionalFee) {
	this.additionalFee = additionalFee;
    }

    public Double getTotalPrice() {
	return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
	this.totalPrice = totalPrice;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }








}
