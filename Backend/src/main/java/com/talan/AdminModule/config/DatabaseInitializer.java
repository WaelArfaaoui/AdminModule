package com.talan.AdminModule.config;

import com.talan.AdminModule.dto.ColumnInfo;
import com.talan.AdminModule.dto.TableInfo;
import com.talan.AdminModule.service.ParamTableService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class DatabaseInitializer {

    @Autowired
    private ParamTableService tableService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        List<TableInfo> allTablesWithColumns = tableService.retrieveAllTablesWithColumns();

        for (TableInfo tableInfo : allTablesWithColumns) {
            List<ColumnInfo> columns = tableInfo.getColumns();

            String active="active";

            List<String> columnNames= new ArrayList<>();
            for (ColumnInfo column :columns){
               columnNames.add(column.getName());
            }

            if (!columnNames.contains(active)&&!tableInfo.getName().contains("payment")){
                jdbcTemplate.execute("ALTER TABLE " + tableInfo.getName() + " ADD COLUMN "+ active +" BOOLEAN DEFAULT TRUE");
            }
        }
    }

}
