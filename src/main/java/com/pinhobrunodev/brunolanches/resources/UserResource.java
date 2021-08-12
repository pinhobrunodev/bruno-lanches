package com.pinhobrunodev.brunolanches.resources;


import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.user.ShowUserInfoDTO;
import com.pinhobrunodev.brunolanches.dto.user.ShowUserOrderDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserRegisterDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserUpdateDTO;
import com.pinhobrunodev.brunolanches.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;



    @PostMapping(value = "/save")
    public ResponseEntity<Void> save(@RequestBody UserRegisterDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        service.save(dto);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ShowUserInfoDTO> update(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        return ResponseEntity.ok().body(service.update(dto, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/by-id/{id}")
    public ResponseEntity<ShowUserInfoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = "/by-name/{name}")
    public ResponseEntity<ShowUserInfoDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ShowUserInfoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/orders-pending/{id}")
    public ResponseEntity<List<ShowUserOrderDTO>> showAllPendingOrdersByUserId(@PathVariable Long id){
        return ResponseEntity.ok().body(service.showAllPendingOrdersByUserId(id));
    }

    @GetMapping(value = "/orders-delivered/{id}")
    public ResponseEntity<List<ShowUserOrderDTO>> showAllDeliveredOrdersByUserId(@PathVariable Long id){
        return ResponseEntity.ok().body(service.showAllDeliveredOrdersByUserId(id));
    }



}
