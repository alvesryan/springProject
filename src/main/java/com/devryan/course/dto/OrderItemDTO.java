package com.devryan.course.dto;

import com.devryan.course.entities.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public class OrderItemDTO implements Serializable {

    @NotBlank(message = "Nome do produto é obrigatório")
    private String name;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantity;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    private Double price;

    private Double subTotal;

    public OrderItemDTO(){

    }

    public OrderItemDTO(OrderItem entity){
        this.name = entity.getProduct() != null ? entity.getProduct().getName() : null;
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
