package com.talan.AdminModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteRequest {
    private String tableName;
    private String primaryKeyValue;
    private String ussername;

}
