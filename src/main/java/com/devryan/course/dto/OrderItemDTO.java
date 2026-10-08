package com.devryan.course.dto;

import com.devryan.course.entities.OrderItem;

import java.io.Serializable;

public class OrderItemDTO implements Serializable {

    private String name;
    private Integer quantity;
    private Double price;
    private Double subTotal;

    public OrderItemDTO(){

    }

    public OrderItemDTO(OrderItem entity){
        this.name = entity.getProduct().getName();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.subTotal = entity.getSubTotal();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
