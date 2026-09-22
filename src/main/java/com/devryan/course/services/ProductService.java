package com.devryan.course.services;

import com.devryan.course.entities.Order;
import com.devryan.course.entities.Product;
import com.devryan.course.repositories.OrderRepository;
import com.devryan.course.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        productRepository.findById(id);
        Optional<Product> product = productRepository.findById(id); //Optional<T> é uma classe que serve para evitar o nullPointerException.
        return product.orElseThrow(); // .orElseThrow dispara uma exception caso não exista algum com esse id
    }
}
