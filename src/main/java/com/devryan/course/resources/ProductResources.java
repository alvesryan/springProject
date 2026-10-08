package com.devryan.course.resources;

import com.devryan.course.dto.ProductDTO;
import com.devryan.course.entities.Product;
import com.devryan.course.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

    @Autowired
    private ProductService productService;

    @GetMapping// Mapeia requisições http do tipo GET
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<Product> products = productService.findAll();

        List<ProductDTO> productDTOS = products.stream().map(ProductDTO::new).toList();
        return ResponseEntity.ok().body(productDTOS);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        Product product = productService.findById(id);

        ProductDTO productDTO = new ProductDTO(product);
        return ResponseEntity.ok().body(productDTO);
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody Product product){
        product = productService.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){
        product = productService.update(id, product);
        return ResponseEntity.ok().body(product);
    }
}
