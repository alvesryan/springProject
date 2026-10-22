package com.devryan.course.dto;

import com.devryan.course.entities.Category;
import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(Long id, @NotBlank(message = "Nome é obrigatório") String name) {

    public CategoryDTO(Category entity) {
        this(entity.getId(), entity.getName());
    }

    public Category toEntity() {
        return new Category(this.id, this.name);
    }
}