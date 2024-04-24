package com.talan.AdminModule.service;

import com.talan.AdminModule.dto.AttributeDto;
import com.talan.AdminModule.entity.Attribute;

import java.util.List;

public interface AttributeService {
    AttributeDto save(AttributeDto attributeDTO);

    void delete(Integer id);

    AttributeDto findById(Integer id);

    List<AttributeDto> findAll();

    boolean existByName(String name) ;


}
