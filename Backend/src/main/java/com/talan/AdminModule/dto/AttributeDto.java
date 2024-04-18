package com.talan.AdminModule.dto;

import com.talan.AdminModule.entity.Attribute;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttributeDto{

    private Integer id;

    private String name;

    private Double percentage ;
    private Double value ;

    public static AttributeDto fromEntity(Attribute attribute) {
        if (attribute == null) {
            return null;
        }
        return AttributeDto.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .build();
    }

    public static Attribute toEntity(AttributeDto attributeDTO) {
        if (attributeDTO == null) {
            return null;
        }
        Attribute attribute = new Attribute();
        attribute.setName(attributeDTO.getName());
        return attribute;
    }
}