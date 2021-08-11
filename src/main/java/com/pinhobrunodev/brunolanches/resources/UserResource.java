package com.pinhobrunodev.brunolanches.resources;


import com.pinhobrunodev.brunolanches.dto.user.ShowUserInfoDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserRegisterDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserUpdateDTO;
import com.pinhobrunodev.brunolanches.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * TODO
     * /users => OK
     * /user/{name} => OK
     * /user/{id} => OK
     * /user/{id}/orders
     * /users/{id}/orders/status/pending
     * /users/{id}/orders/status/delivered
     * /users/save => OK
     * /users/update/{id} => OK
     * /users/delete/{id} => OK
     **/


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

    @GetMapping(value = "/{id}")
    public ResponseEntity<ShowUserInfoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<ShowUserInfoDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ShowUserInfoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }


}
