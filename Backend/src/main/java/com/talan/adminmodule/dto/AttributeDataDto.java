package com.talan.AdminModule.dto;

import com.talan.AdminModule.entity.Attribute;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttributeDataDto{
    private Integer id ;
    private AttributeDto name ;
    private Double percentage ;
    private Double value ;
}