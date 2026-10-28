package com.devryan.course.dto;

import com.devryan.course.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "the name cannot be empty")
        @Size(min = 3, message = "Minimum of 3 characters")
        String name,

        @NotBlank(message = "the email cannot be empty")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "the phone cannot be empty")
        String phone,

        @NotBlank(message = "the password cannot be empty")
        @Size(min = 6, message = "minium 6 characters")
        String password){

        // DTO -> Entity
        public User toEntity(){
                return new User(null, name, email, phone, password);
        }
}
