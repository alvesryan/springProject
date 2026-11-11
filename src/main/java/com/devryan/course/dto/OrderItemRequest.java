package com.devryan.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotBlank(message = "Nome do produto é obrigatório")
        String name,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantity,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        Double price
) {
}
