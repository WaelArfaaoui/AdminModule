package com.talan.AdminModule.service.impl;

import com.talan.AdminModule.dto.AttributeDto;
import com.talan.AdminModule.entity.Attribute;
import com.talan.AdminModule.repository.AttributeRepository;
import com.talan.AdminModule.service.AttributeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;

    @Autowired
    public AttributeServiceImpl(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }
    @Override
    public AttributeDto save(AttributeDto attributeDTO) {
        return AttributeDto.fromEntity(attributeRepository.save(AttributeDto.toEntity(attributeDTO)));
    }

    @Override
    public void delete(Integer id) {
        this.attributeRepository.deleteById(id);
    }

    @Override
    public AttributeDto findById(Integer id) {

        Attribute attribute  = this.attributeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "No attribute with ID = " + id + " found in the database")
        );
        return AttributeDto.fromEntity(attribute);
    }

    @Override
    public List<AttributeDto> findAll() {
        return attributeRepository.findAll().stream()
                .map(AttributeDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existByName(String name) {
        return this.attributeRepository.existsByName(name);
    }


}
