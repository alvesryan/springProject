package com.devryan.course.dto;

import com.devryan.course.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String phone;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // Construtor vazio padrão
    public UserDTO() {
    }

    // Construtor que facilita transformar a Entidade (User) em DTO (UserDTO)
    // Quando puxarmos o User do banco, passamos ele aqui para copiar os dados
    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.password = entity.getPassword();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //transforma um UserDto em User
    public User toEntity(){
        return new User(this.id, this.name, this.email, this.phone, this.password);
    }
}
