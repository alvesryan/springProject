package com.devryan.course.services;

import com.devryan.course.entities.Category;
import com.devryan.course.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        categoryRepository.findById(id);
        Optional<Category> order = categoryRepository.findById(id); //Optional<T> é uma classe que serve para evitar o nullPointerException.
        return order.orElseThrow(); // .orElseThrow dispara uma exception caso não exista algum com esse id
    }
}
