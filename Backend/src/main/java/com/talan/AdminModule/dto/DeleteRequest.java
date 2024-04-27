package com.talan.AdminModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DeleteRequest {
    private String tableName;
    private String primaryKeyValue;
    private String ussername;

    public DeleteRequest(String tableName, String primaryKeyValue, String ussername) {
        this.tableName = tableName;
        this.primaryKeyValue = primaryKeyValue;
        this.ussername = ussername;
    }
}
