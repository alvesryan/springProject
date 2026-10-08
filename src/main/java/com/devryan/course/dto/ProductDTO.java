package com.devryan.course.dto;

import com.devryan.course.entities.Product;

import java.io.Serializable;

public class ProductDTO implements Serializable {

    private String name;
    private String description;
    private Double price;

    public ProductDTO() {
    }

    public ProductDTO(Product entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
