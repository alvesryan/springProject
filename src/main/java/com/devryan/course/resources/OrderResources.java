package com.devryan.course.resources;

import com.devryan.course.entities.Order;
import com.devryan.course.entities.User;
import com.devryan.course.services.OrderService;
import com.devryan.course.services.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderResources {

    @Autowired
    // Realiza uma injeção de dependência, ele instancia o UserService e o injeta direto na variável, sem necessidade de um new
    private OrderService orderService;

    @GetMapping// Mapeia requisições http do tipo GET
    public ResponseEntity<List<Order>> findAll(){
        List<Order> list = orderService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    //ResponseEntity<T> é uma classe do spring que é usada para encapsular toda a resposta HTTP. Permite manipular não só o corpo da resposta mas também o status code
    public ResponseEntity<Order> findById(@PathVariable Long id){ //Pega o valor do id que veio na URL e injeta na variável id do método
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }
}
