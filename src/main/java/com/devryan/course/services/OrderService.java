package com.devryan.course.services;

import com.devryan.course.entities.Order;
import com.devryan.course.repositories.OrderRepository;
import com.devryan.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(Long id){
        orderRepository.findById(id);
        Optional<Order> order = orderRepository.findById(id); //Optional<T> é uma classe que serve para evitar o nullPointerException.
        return order.orElseThrow(() -> new ResourceNotFoundException(id)); // .orElseThrow dispara uma exception caso não exista algum com esse id
    }

    public Order insert(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Long id){
        try {
            if(!orderRepository.existsById(id)){
                throw new ResourceNotFoundException(id);
            }
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public Order update(Long id, Order order){
        try {
            Order entity = orderRepository.getReferenceById(id);
            updateData(entity, order);
            return orderRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Order entity, Order order){
        if (order.getOrderStatus() != null) {
            entity.setOrderStatus(order.getOrderStatus());
        }
    }
}
