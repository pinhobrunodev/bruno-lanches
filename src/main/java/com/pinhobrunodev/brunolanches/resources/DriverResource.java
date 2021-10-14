package com.pinhobrunodev.brunolanches.resources;


import com.pinhobrunodev.brunolanches.dto.driver.DriverPagedSearchDTO;
import com.pinhobrunodev.brunolanches.dto.driver.InsertDriverDTO;
import com.pinhobrunodev.brunolanches.dto.driver.ShowDriverInfoDTO;
import com.pinhobrunodev.brunolanches.dto.driver.UpdateDriverDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.services.DriverService;
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
@RequestMapping(value = "/drivers")
public class DriverResource {

    @Autowired
    private DriverService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Void> save(@Valid @RequestBody InsertDriverDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        service.save(dto);
        return ResponseEntity.created(uri).build();
    }


    @PostMapping(value = "/take-orders-pending/{id}/driver-id/{driver_id}")
    public ResponseEntity<Void> takePendingOrder(@PathVariable Long id, @PathVariable Long driver_id) {
        service.takePendingOrder(id, driver_id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/set-delivered/{id}")
    public ResponseEntity<Void> setDelivered(@PathVariable Long id) {
        service.setDelivered(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ShowDriverInfoDTO> update(  @PathVariable Long id, @Valid @RequestBody UpdateDriverDTO dto) {
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

    @GetMapping(value = "/by-name")
    public ResponseEntity<ShowDriverInfoDTO> findByName(@RequestParam String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ShowDriverInfoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }


    @GetMapping(value = "/orders/pending")
    public ResponseEntity<Page<ShowOrderInfoDTO>> showPendingOrders(Pageable pageable) {
        return ResponseEntity.ok().body(service.showPendingOrders(pageable));
    }

    @GetMapping(value = "/paged-search")
    public ResponseEntity<Page<DriverPagedSearchDTO>> pagedSearch(Pageable pageable) {
        return ResponseEntity.ok().body(service.pagedSearch(pageable));
    }

    @GetMapping(value = "/my-delivered-orders/{id}")
    public ResponseEntity<List<ShowOrderInfoDTO>> showAllDeliveredOrdersByDriverId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.showAllDeliveredOrdersByDriverId(id));
    }


    // *
    @GetMapping(value = "/my-current-order/{id}")
    public ResponseEntity<List<ShowOrderInfoDTO>> showAllInProgressOrdersByDriverId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.showAllInProgressOrdersByDriverId(id));
    }
}
