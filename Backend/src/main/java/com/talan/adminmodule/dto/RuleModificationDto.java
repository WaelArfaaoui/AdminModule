package com.talan.adminmodule.dto;

import com.talan.adminmodule.entity.RuleModification;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RuleModificationDto {

    private Integer id;
    private RuleDto ruleDto;
    private LocalDateTime modificationDate;
    private Integer modifiedBy;
    private String ruleName;
    private String modificationDescription;

    public static RuleModificationDto fromEntity(RuleModification ruleModification) {
        if (ruleModification == null) {
            return null;
        }
        return RuleModificationDto.builder()
                .id(ruleModification.getId())
                .modificationDate(ruleModification.getModificationDate())
                .modifiedBy(ruleModification.getModifiedBy())
                .ruleName(ruleModification.getRuleName())
                .modificationDescription(ruleModification.getModificationDescription())
                .build();
    }
}