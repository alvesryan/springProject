package com.devryan.course.resources;

import com.devryan.course.entities.Category;
import com.devryan.course.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

    @Autowired
    // Realiza uma injeção de dependência, ele instancia o UserService e o injeta direto na variável, sem necessidade de um new
    private CategoryService categoryService;

    @GetMapping// Mapeia requisições http do tipo GET
    public ResponseEntity<List<Category>> findAll(){
        List<Category> list = categoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    //ResponseEntity<T> é uma classe do spring que é usada para encapsular toda a resposta HTTP. Permite manipular não só o corpo da resposta mas também o status code
    public ResponseEntity<Category> findById(@PathVariable Long id){ //Pega o valor do id que veio na URL e injeta na variável id do método
        Category category = categoryService.findById(id);
        return ResponseEntity.ok().body(category);
    }
}
