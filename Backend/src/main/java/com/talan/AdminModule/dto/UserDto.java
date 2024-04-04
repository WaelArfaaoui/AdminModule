package com.talan.AdminModule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.talan.AdminModule.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String profileImagePath;
    private String phone;
    private String company ;
    private Role role;
    private String error;
}
