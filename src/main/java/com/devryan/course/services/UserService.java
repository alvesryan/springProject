package com.devryan.course.services;

import com.devryan.course.entities.User;
import com.devryan.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // diz ao spring, essa classe contém regras de negócio e deve ser registrada no seu container para poder ser injetada em outros lugares
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        repository.findById(id);
        Optional<User> user = repository.findById(id); //Optional<T> é uma classe que serve para evitar o nullPointerException.
        return user.orElseThrow(); // .orElseThrow dispara uma exception caso não exista algum com esse id
    }
}
