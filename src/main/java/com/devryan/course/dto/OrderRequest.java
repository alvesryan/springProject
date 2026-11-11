package com.devryan.course.dto;

import com.devryan.course.entities.Order;
import com.devryan.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record OrderRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        Instant moment,

        @NotNull(message = "Order status cannot be empty")
        Integer orderStatus,

        @Valid
        @NotNull(message = "" +
                "Client cannot be null")
        UserResponse client
) {
    public Order toEntity() {
        Order order = new Order();
        order.setMoment(moment != null ? moment : Instant.now());
        if (orderStatus != null) {
            order.setOrderStatus(OrderStatus.valueOf(orderStatus));
        }
        if (client != null) {
            order.setClient(client.toEntity());
        }
        return order;
    }
}
