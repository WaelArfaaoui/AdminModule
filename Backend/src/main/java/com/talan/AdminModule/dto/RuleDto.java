package com.talan.AdminModule.dto;

import com.talan.AdminModule.entity.Rule;
import com.talan.AdminModule.entity.RuleModification;
import lombok.Builder;
import lombok.Data;
import org.w3c.dom.Attr;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RuleDto {

    private Integer id;
    private String name;
    private String description;
    private CategoryDto category ;
    private boolean enabled;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    private Integer createdBy;
    private Integer lastModifiedBy;
    private List<AttributeDataDto> attributeDtos ;


    public static RuleDto fromEntity(Rule rule) {
        if (rule == null) {
            return null;
        }
        RuleDto ruleDto = RuleDto.builder()
                .id(rule.getId())
                .name(rule.getName())
                .description(rule.getDescription())
                .enabled(rule.isEnabled())
                .createDate(rule.getCreateDate())
                .lastModified(rule.getLastModified())
                .createdBy(rule.getCreatedBy())
                .lastModifiedBy(rule.getLastModifiedBy())
                .build();

        List<AttributeDataDto> attributeDtos = rule.getRuleAttributes().stream()
                .map(ruleAttribute -> AttributeDataDto.builder()
                        .id(ruleAttribute.getAttribute().getId())
                        .name(AttributeDto.builder().id(ruleAttribute.getAttribute().getId()).name(ruleAttribute.getAttribute().getName()).build())
                        .percentage(ruleAttribute.getPercentage())
                        .value(ruleAttribute.getValue())
                        .build())
                .collect(Collectors.toList());

        ruleDto.setAttributeDtos(attributeDtos);

        return ruleDto;
    }


    public static Rule toEntity(RuleDto ruleDTO) {
        if (ruleDTO == null) {
            return null;
        }
        Rule rule = new Rule();
        rule.setName(ruleDTO.getName());
        rule.setDescription(ruleDTO.getDescription());
        rule.setEnabled(ruleDTO.isEnabled());
        return rule;
    }
}