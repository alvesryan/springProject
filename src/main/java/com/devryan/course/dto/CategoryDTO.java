package com.devryan.course.dto;

import com.devryan.course.entities.Category;
import com.devryan.course.entities.Product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    private Set<ProductDTO> items = new HashSet<>();

    public CategoryDTO(){
    }

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
        
        for(Product product : entity.getProducts()) {
            this.items.add(new ProductDTO(product));
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
}
