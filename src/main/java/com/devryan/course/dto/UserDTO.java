package com.devryan.course.dto;

import com.devryan.course.entities.User;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String name;
    private String email;
    private String phone;

    // Construtor vazio padrão
    public UserDTO() {
    }

    // Construtor que facilita transformar a Entidade (User) em DTO (UserDTO)
    // Quando puxarmos o User do banco, passamos ele aqui para copiar os dados
    public UserDTO(User entity) {
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
    }

    // Apenas Getters e Setters, sem regra de negócio e sem anotações do JPA (@Entity, @Table, etc)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
