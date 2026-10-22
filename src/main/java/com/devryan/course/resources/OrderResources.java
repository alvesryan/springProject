package com.devryan.course.resources;

import com.devryan.course.dto.OrderDTO;
import com.devryan.course.entities.Order;
import com.devryan.course.services.OrderService;
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
@RequestMapping(value = "/orders")
public class OrderResources {

    @Autowired
    private OrderService orderService;

    @GetMapping// Mapeia requisições http do tipo GET
    public ResponseEntity<Page<OrderDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "moment", direction = Sort.Direction.ASC) Pageable pageable){
        Page<OrderDTO> page = orderService.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        Order order = orderService.findById(id);

        OrderDTO orderDTO = new OrderDTO(order);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody @Valid OrderDTO dto){
        Order order = dto.toEntity();
        order = orderService.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(new OrderDTO(order));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody @Valid OrderDTO dto){
        Order order = dto.toEntity();
        order = orderService.update(id, order);
        return ResponseEntity.ok().body(new OrderDTO(order));
    }
}
