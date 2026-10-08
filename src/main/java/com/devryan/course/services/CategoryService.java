package com.devryan.course.services;

import com.devryan.course.entities.Category;
import com.devryan.course.repositories.CategoryRepository;
import com.devryan.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        Optional<Category> order = categoryRepository.findById(id);
        return order.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Category insert(Category category){
        return categoryRepository.save(category);
    }

    public void delete(Long id){
        try{
            if(!categoryRepository.existsById(id)){
                throw new ResourceNotFoundException(id);
            }
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public Category update(Long id, Category category){
        try{
            Category entity = categoryRepository.getReferenceById(id);
            updateData(entity, category);
            return categoryRepository.save(entity);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Category entity, Category category){
        if(category.getName() != null){
            entity.setName(category.getName());
        }
    }
}
