package com.pinhobrunodev.brunolanches.resources;


import com.pinhobrunodev.brunolanches.dto.driver.RegisterDriverDTO;
import com.pinhobrunodev.brunolanches.dto.driver.ShowDriverInfoDTO;
import com.pinhobrunodev.brunolanches.dto.driver.TakeOrderDTO;
import com.pinhobrunodev.brunolanches.dto.driver.UpdateDriverDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.user.ShowUserInfoDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserRegisterDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserUpdateDTO;
import com.pinhobrunodev.brunolanches.services.DriverService;
import com.pinhobrunodev.brunolanches.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/drivers")
public class DriverResource {

    @Autowired
    private DriverService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Void> save(@RequestBody RegisterDriverDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        service.save(dto);
        return ResponseEntity.created(uri).build();
    }


    @PostMapping(value = "/take-orders-pending/{id}")
    public ResponseEntity<Void> takePendingOrder(@PathVariable Long id, @RequestBody TakeOrderDTO dto) {
        service.takePendingOrder(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ShowDriverInfoDTO> update(@PathVariable Long id, @RequestBody UpdateDriverDTO dto) {
        return ResponseEntity.ok().body(service.update(dto, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/by-id/{id}")
    public ResponseEntity<ShowDriverInfoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = "/by-name/{name}")
    public ResponseEntity<ShowDriverInfoDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ShowDriverInfoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }


    @GetMapping(value = "/orders/pending")
    public ResponseEntity<List<ShowOrderInfoDTO>> showPendingOrders() {
        return ResponseEntity.ok().body(service.showPendingOrders());
    }
}
