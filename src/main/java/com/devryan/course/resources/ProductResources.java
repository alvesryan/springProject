package com.devryan.course.resources;

import com.devryan.course.dto.ProductRequest;
import com.devryan.course.dto.ProductResponse;
import com.devryan.course.entities.Product;
import com.devryan.course.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findAll(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        Page<ProductResponse> page = productService.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id){
        Product product = productService.findById(id);
        ProductResponse response = new ProductResponse(product);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> insert(@RequestBody @Valid ProductRequest request){
        Product product = request.toEntity();
        product = productService.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductResponse(product));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody @Valid ProductRequest request){
        Product product = request.toEntity();
        product = productService.update(id, product);
        return ResponseEntity.ok().body(new ProductResponse(product));
    }
}
