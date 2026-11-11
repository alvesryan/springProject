package com.devryan.course.dto;

import com.devryan.course.entities.OrderItem;

public record OrderItemResponse(
        String name,
        Integer quantity,
        Double price,
        Double subTotal
) {
    public OrderItemResponse(OrderItem entity) {
        this(
                entity.getProduct() != null ? entity.getProduct().getName() : null,
                entity.getQuantity(),
                entity.getPrice(),
                entity.getSubTotal()
        );
    }
}
