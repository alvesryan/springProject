package com.devryan.course.dto;

import com.devryan.course.entities.Order;
import com.devryan.course.entities.OrderItem;
import com.devryan.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class OrderDTO implements Serializable {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment;

    @NotNull(message = "Status do pedido é obrigatório")
    private Integer orderStatus;

    @Valid
    private UserDTO client;

    private Set<@Valid OrderItemDTO> items = new HashSet<>();
    private Double total;

    public OrderDTO() {
    }

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.orderStatus = entity.getOrderStatus() != null ? entity.getOrderStatus().getCode() : null;
        this.client = entity.getClient() != null ? new UserDTO(entity.getClient()) : null;
        this.total = entity.getTotal();

        if (entity.getItems() != null) {
            for(OrderItem item: entity.getItems()){
                this.items.add(new OrderItemDTO(item));
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDTO> items) {
        this.items = items;
    }

    public Order toEntity() {
        Order order = new Order();
        order.setId(this.id);
        order.setMoment(this.moment != null ? this.moment : Instant.now());
        if (this.orderStatus != null) {
            order.setOrderStatus(OrderStatus.valueOf(this.orderStatus));
        }
        if (this.client != null) {
            order.setClient(this.client.toEntity());
        }
        return order;
    }
}
