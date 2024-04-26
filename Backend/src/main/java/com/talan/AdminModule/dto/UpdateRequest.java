package com.talan.AdminModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateRequest {
    Map<String, String> instanceData;
    String tableName;
    String username;
}
