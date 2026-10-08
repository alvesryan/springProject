package com.devryan.course.services;

import com.devryan.course.entities.User;
import com.devryan.course.repositories.UserRepository;
import com.devryan.course.services.exceptions.DatabaseException;
import com.devryan.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // diz ao spring, essa classe contém regras de negócio e deve ser registrada no seu container para poder ser injetada em outros lugares
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        userRepository.findById(id);
        Optional<User> user = userRepository.findById(id); //Optional<T> é uma classe que serve para evitar o nullPointerException.
        return user.orElseThrow(() -> new ResourceNotFoundException(id)); // .orElseThrow dispara uma exception caso não exista algum com esse id
    }

    public User insert(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        try {
            if (!userRepository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(Long id, User user){
        try {
            User entity = userRepository.getReferenceById(id); //
            updateData(entity, user);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User user) {
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
    }
}
