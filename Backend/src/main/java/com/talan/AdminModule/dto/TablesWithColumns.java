package com.talan.AdminModule.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TablesWithColumns {
    private Long numberTables;
    private List<TableInfo> allTablesWithColumns;
}
