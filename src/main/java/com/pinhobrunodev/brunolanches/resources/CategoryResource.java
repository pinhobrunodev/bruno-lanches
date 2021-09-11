package com.pinhobrunodev.brunolanches.resources;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Void> save(@Valid  @RequestBody CategoryDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        service.save(dto);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @PathVariable Long id, @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/find-by-id/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }


    @GetMapping(value = "/find-by-name")
    public ResponseEntity<CategoryDTO> findByName(@RequestParam String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }


    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
