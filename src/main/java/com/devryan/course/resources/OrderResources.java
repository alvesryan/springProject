package com.devryan.course.resources;

import com.devryan.course.dto.OrderRequest;
import com.devryan.course.dto.OrderResponse;
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

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> findAll(@PageableDefault(page = 0, size = 10, sort = "moment", direction = Sort.Direction.ASC) Pageable pageable){
        Page<OrderResponse> page = orderService.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        Order order = orderService.findById(id);
        OrderResponse response = new OrderResponse(order);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> insert(@RequestBody @Valid OrderRequest request){
        Order order = request.toEntity();
        order = orderService.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(new OrderResponse(order));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody @Valid OrderRequest request){
        Order order = request.toEntity();
        order = orderService.update(id, order);
        return ResponseEntity.ok().body(new OrderResponse(order));
    }
}
