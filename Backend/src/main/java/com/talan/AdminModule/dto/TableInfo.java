package com.talan.AdminModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {
    private String name;
    private String type;
    private String pk;
    private String remarks;
    private List<ColumnInfo> columns;


}
