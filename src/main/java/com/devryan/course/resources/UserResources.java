package com.devryan.course.resources;

import com.devryan.course.entities.User;
import com.devryan.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  // junção de @Controller + @ResponseBody, controla o acesso à web e diz que o retorno dos métodos iram ser convertidos em JSON
@RequestMapping(value = "/users") // Define o caminho base da URL para essa classe
public class UserResources {

    @Autowired // Realiza uma injeção de dependência, ele instancia o UserService e o injeta direto na variável, sem necessidade de um new
    private UserService userService;

    @GetMapping// Mapeia requisições http do tipo GET
    public ResponseEntity<List<User>> findAll(){
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    //ResponseEntity<T> é uma classe do spring que é usada para encapsular toda a resposta HTTP. Permite manipular não só o corpo da resposta mas também o status code
    public ResponseEntity<User> findById(@PathVariable Long id){ //Pega o valor do id que veio na URL e injeta na variável id do método
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }
}
