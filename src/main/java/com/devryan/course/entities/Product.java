package com.devryan.course.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    @ManyToMany
    @JoinTable(
            name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "id.product")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<OrderItem> itens = new HashSet<>();

    public Product(Long id, String name, String description, Double price) {
        this(id, name, description, price, null);
    }

    public Product(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    @JsonIgnore
    public Set<Order> getOrders() {
        Set<Order> set = new HashSet<>();
        for (OrderItem x : itens) {
            set.add(x.getOrder());
        }
        return set;
    }
}
