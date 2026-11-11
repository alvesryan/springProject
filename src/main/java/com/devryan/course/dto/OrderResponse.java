package com.devryan.course.dto;

import com.devryan.course.entities.Order;
import com.devryan.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        Instant moment,
        OrderStatus orderStatus,
        UserResponse client,
        Set<OrderItemResponse> items,
        Double total
) {
    public OrderResponse(Order entity) {
        this(
                entity.getId(),
                entity.getMoment(),
                entity.getOrderStatus(),
                entity.getClient() != null ? new UserResponse(entity.getClient()) : null,
                entity.getItems() != null
                        ? entity.getItems().stream().map(OrderItemResponse::new).collect(Collectors.toSet())
                        : Collections.emptySet(),
                entity.getTotal()
        );
    }
}
