package com.devryan.course.dto;

import com.devryan.course.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        String description,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        Double price,

        String imgUrl
) {
    public Product toEntity() {
        return new Product(null, name, description, price, imgUrl);
    }
}
