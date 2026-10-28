package com.devryan.course.dto;

import com.devryan.course.entities.User;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone) {

    //construtor para ler a entidade do banco
    public UserResponse(User entity){
        this(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone());
    }
}
