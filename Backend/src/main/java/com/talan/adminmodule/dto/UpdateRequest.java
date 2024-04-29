package com.talan.adminmodule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class UpdateRequest {
    Map<String, String> instanceData;
    String tableName;

    public UpdateRequest(Map<String, String> instanceData, String tableName, String username) {
        this.instanceData = instanceData;
        this.tableName = tableName;
        this.username = username;
    }

    String username;
}
