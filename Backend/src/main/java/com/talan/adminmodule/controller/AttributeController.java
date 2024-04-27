package com.talan.adminmodule.controller;

import com.talan.adminmodule.dto.AttributeDto;
import com.talan.adminmodule.service.AttributeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Attribute")
@RequestMapping("api/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    @Autowired
    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping
    public ResponseEntity<AttributeDto> createAttribute(@RequestBody AttributeDto attributeDto) {
        AttributeDto createdAttribute = attributeService.save(attributeDto);
        return new ResponseEntity<>(createdAttribute, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeDto> getAttributeById(@PathVariable Integer id) {
        AttributeDto attributeDto = attributeService.findById(id);
        return ResponseEntity.ok(attributeDto);
    }

    @GetMapping
    public ResponseEntity<List<AttributeDto>> getAllAttributes() {
        List<AttributeDto> attributeDtoList = attributeService.findAll();
        return ResponseEntity.ok(attributeDtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable Integer id) {
        attributeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> doesAttributeExistByName(@PathVariable String name) {
        boolean exists = attributeService.existByName(name);
        return ResponseEntity.ok(exists);
    }
}
