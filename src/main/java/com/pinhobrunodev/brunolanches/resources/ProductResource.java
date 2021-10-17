package com.pinhobrunodev.brunolanches.resources;


import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.services.ProductService;
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
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService service;


    @PostMapping(value = "/save")
    public ResponseEntity<Void> save( @Valid  @RequestBody ProductDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        service.save(dto);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,  @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.ok().body(service.update(dto, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/find-by-id/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }


    @GetMapping(value = "/find-by-name")
    public ResponseEntity<ProductDTO> findByName(@RequestParam String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }


    // Available parameters -> ?page=0&size=10&sort=price,ASC
    @GetMapping(value = "/paged-search")
    public ResponseEntity<Page<ProductDTO>> pagedSearch(Pageable pageable) {
        return ResponseEntity.ok().body(service.pagedSearch(pageable));
    }


    @GetMapping()
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

}
