package com.pinhobrunodev.brunolanches.resources;


import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.client.ShowClientInfoDTO;
import com.pinhobrunodev.brunolanches.dto.client.ClientInsertDTO;
import com.pinhobrunodev.brunolanches.dto.client.ClientPagedSearchDTO;
import com.pinhobrunodev.brunolanches.dto.client.ClientUpdateDTO;
import com.pinhobrunodev.brunolanches.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService service;


    @PostMapping(value = "/save")
    public ResponseEntity<Void> save( @Valid @RequestBody ClientInsertDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        service.save(dto);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ShowClientInfoDTO> update(@PathVariable Long id, @Valid @RequestBody ClientUpdateDTO dto) {
        return ResponseEntity.ok().body(service.update(dto, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/by-id/{id}")
    public ResponseEntity<ShowClientInfoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = "/by-name")
    public ResponseEntity<ShowClientInfoDTO> findByName(@RequestParam String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ShowClientInfoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/orders-pending/{id}")
    public ResponseEntity<List<ShowOrderInfoDTO>> showAllPendingOrdersByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.showAllPendingOrdersByUserId(id));
    }

    @GetMapping(value = "/orders-delivered/{id}")
    public ResponseEntity<List<ShowOrderInfoDTO>> showAllDeliveredOrdersByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.showAllDeliveredOrdersByUserId(id));
    }

    @GetMapping(value = "/orders-in-progress/{id}")
    public ResponseEntity<List<ShowOrderInfoDTO>> showAllInProgressOrdersByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.showAllInProgressOrdersByUserId(id));
    }


    @GetMapping(value = "/paged-search")
    public ResponseEntity<Page<ClientPagedSearchDTO>> pagedSearch(Pageable pageable) {
        return ResponseEntity.ok().body(service.pagedSearch(pageable));
    }


}
