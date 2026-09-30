package com.devryan.course.entities;

import com.devryan.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.metamodel.mapping.ordering.ast.OrderingExpression;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT") //anotação jackson que serve para ditar como um campo json deve ser formatado
    private Instant moment;

    private Integer orderStatus;

    @ManyToOne //anotação JPA que mapeia relacionamento "Muitos para um", muitos registros dessa classe podem estar associados a uma outra classe
    @JoinColumn(name = "client_id") // serve para nomear o nome da coluna onde a chave estrangeira será armazenada
    private User client;

    @OneToMany(mappedBy = "id.order") //no orderItem eu tenho o id e no id eu tenho o order;
    private Set<OrderItem> items = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL) //o pagamento vai ter o mesmo id do pedido
    private Payment payment;

    public Order() {
    }

    public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
        this.id = id;
        this.moment = moment;
        this.client = client;
        setOrderStatus(orderStatus);
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

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(orderStatus);
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        if(orderStatus != null){
            this.orderStatus = orderStatus.getCode();
        }
    }

    public Double getTotal(){
        double sum = 0.0;
        for(OrderItem x : items){
            sum += x.getSubTotal();
        }

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
