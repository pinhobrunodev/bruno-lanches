package com.pinhobrunodev.brunolanches.resources;


import com.pinhobrunodev.brunolanches.dto.order.OrderDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {


    @Autowired
    private OrderService service;

    @PostMapping(value = "/create")
    public ResponseEntity<Void> save(@RequestBody OrderDTO dto) {
        service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<ShowOrderInfoDTO>> findAll(){
        return  ResponseEntity.ok().body(service.findAll());
    }
}
