package com.devryan.course.resources;

import com.devryan.course.dto.OrderDTO;
import com.devryan.course.entities.Order;
import com.devryan.course.entities.Product;
import com.devryan.course.entities.User;
import com.devryan.course.services.OrderService;
import com.devryan.course.services.ProductService;
import com.devryan.course.services.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderResources {

    @Autowired
    private OrderService orderService;

    @GetMapping// Mapeia requisições http do tipo GET
    public ResponseEntity<List<OrderDTO>> findAll(){
        List<Order> list = orderService.findAll();

        List<OrderDTO> dtoList = list.stream().map(OrderDTO::new).toList();
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        Order order = orderService.findById(id);

        OrderDTO orderDTO = new OrderDTO(order);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping
    public ResponseEntity<Order> insert(Order order){
        order = orderService.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order){
        order = orderService.update(id, order);
        return ResponseEntity.ok().body(order);
    }
}
