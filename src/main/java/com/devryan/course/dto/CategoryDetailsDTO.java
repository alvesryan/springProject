package com.devryan.course.dto;

import com.devryan.course.entities.Category;
import jakarta.validation.constraints.NotBlank;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record CategoryDetailsDTO(
        Long id,
        @NotBlank(message = "Nome é obrigatório")
        String name,
        Set<ProductResponse> items
) {
    public CategoryDetailsDTO(Category entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getProducts() != null
                        ? entity.getProducts().stream().map(ProductResponse::new).collect(Collectors.toSet())
                        : Collections.emptySet()
        );
    }
}
