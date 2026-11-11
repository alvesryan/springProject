package com.devryan.course.dto;

import com.devryan.course.entities.Product;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        String imgUrl
) {
    public ProductResponse(Product entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl()
        );
    }
}
