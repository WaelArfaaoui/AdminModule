package com.talan.AdminModule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ResponseDto {
    private String success;
    private String error;

    public ResponseDto(String success) {
        this.success = success;
    }

}
