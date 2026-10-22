package com.devryan.course.services;

import com.devryan.course.dto.ProductDTO;
import com.devryan.course.entities.Product;
import com.devryan.course.repositories.ProductRepository;
import com.devryan.course.services.exceptions.DatabaseException;
import com.devryan.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> page = productRepository.findAll(pageable);
        return page.map(ProductDTO::new);
    }

    public Product findById(Long id){
        productRepository.findById(id);
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Product insert(Product product){
        return productRepository.save(product);
    }

    public void delete(Long id){
        try {
            if (!productRepository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Product update (Long id, Product product){
        try {
            Product entity = productRepository.getReferenceById(id);
            updateData(entity, product);
            return productRepository.save(entity);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product entity, Product obj) {
        if (obj.getName() != null) {
            entity.setName(obj.getName());
        }
        if (obj.getDescription() != null) {
            entity.setDescription(obj.getDescription());
        }
        if (obj.getPrice() != null) {
            entity.setPrice(obj.getPrice());
        }
        if (obj.getImgUrl() != null) {
            entity.setImgUrl(obj.getImgUrl());
        }
    }
}
