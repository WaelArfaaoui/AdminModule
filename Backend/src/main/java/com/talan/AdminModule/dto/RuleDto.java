package com.talan.AdminModule.dto;

import com.talan.AdminModule.entity.Rule;
import lombok.Builder;
import lombok.Data;
import org.w3c.dom.Attr;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RuleDto {

    private Integer id;
    private String name;
    private String description;
    private String category ;
    private boolean enabled;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    private Integer createdBy;
    private Integer lastModifiedBy;
    private List<AttributeDto> attributeDtos ;

    public static RuleDto fromEntity(Rule rule) {
        if (rule == null) {
            return null;
        }
        return RuleDto.builder()
                .id(rule.getId())
                .name(rule.getName())
                .description(rule.getDescription())
                .enabled(rule.isEnabled())
                .createDate(rule.getCreateDate())
                .lastModified(rule.getLastModified())
                .createdBy(rule.getCreatedBy())
                .lastModifiedBy(rule.getLastModifiedBy())
                .build();
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