package com.devryan.course.dto;

import com.devryan.course.entities.Category;
import com.devryan.course.entities.Product;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CategoryDetailsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private Set<ProductDTO> items = new HashSet<>();

    public CategoryDetailsDTO() {
    }

    public CategoryDetailsDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDetailsDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();

        if (entity.getProducts() != null) {
            for (Product product : entity.getProducts()) {
                this.items.add(new ProductDTO(product));
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductDTO> getItems() {
        return items;
    }

    public void setItems(Set<ProductDTO> items) {
        this.items = items;
    }

    public Category toEntity() {
        return new Category(this.id, this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDetailsDTO that = (CategoryDetailsDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
