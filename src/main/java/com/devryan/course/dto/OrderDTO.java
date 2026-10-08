package com.devryan.course.dto;

import com.devryan.course.entities.Order;
import com.devryan.course.entities.OrderItem;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class OrderDTO implements Serializable {
    private Long id;
    private Instant moment;
    private Integer orderStatus;
    private UserDTO client;
    private Set<OrderItemDTO> items = new HashSet<>();
    private Double total;

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.orderStatus = entity.getOrderStatus().getCode();
        this.client = new UserDTO(entity.getClient());
        this.total = entity.getTotal();

        for(OrderItem item: entity.getItems()){
            this.items.add(new OrderItemDTO(item));
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
}
