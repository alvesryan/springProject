package com.devryan.course.services;

import com.devryan.course.entities.Order;
import com.devryan.course.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll(){
        return repository.findAll();
    }

    public Order findById(Long id){
        repository.findById(id);
        Optional<Order> order = repository.findById(id); //Optional<T> é uma classe que serve para evitar o nullPointerException.
        return order.orElseThrow(); // .orElseThrow dispara uma exception caso não exista algum com esse id
    }
}
