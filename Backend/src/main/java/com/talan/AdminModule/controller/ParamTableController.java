package com.talan.AdminModule.controller;

import com.talan.AdminModule.dto.TableDataRequest;
import com.talan.AdminModule.dto.TableInfo;
import com.talan.AdminModule.service.ParamTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/api/tables")
@RestController
public class ParamTableController {
    @Autowired
    private ParamTableService tableService;
    @GetMapping()
    public List<TableInfo> retrieveAllTablesAndColumns() {
        return tableService.retrieveAllTablesWithFilteredColumns();
    }
    @GetMapping("/{tableName}")
    public List<Map<String, Object>> getDataFromTable(
            @PathVariable String tableName,
            @ModelAttribute TableDataRequest request
    ) throws SQLException {
        return tableService.getDataFromTable(
                tableName,
                request.getColumns(),
                request.getSortByColumn(),
                request.getSortOrder(),
                request.getLimit(),
                request.getOffset()
        );
    }
    @PutMapping("/update/{tableName}")
    public ResponseEntity<Map<String, Object>> updateInstance(@RequestBody Map<String, String> instanceData,
                                                              @PathVariable String tableName) {
        return ResponseEntity.status(HttpStatus.OK).body(tableService.updateInstance(instanceData, tableName));

    }
    @PostMapping("/{tableName}")
    public Map<String, Object> addInstance(@RequestBody Map<String, String> instanceData,
                                           @PathVariable("tableName") String tableName) {

        return tableService.addInstance(instanceData, tableName);

    }
    @PatchMapping("/{tableName}/delete/{primaryKeyValue}")
    public String updateRecord( @PathVariable String tableName, @PathVariable String primaryKeyValue) {
        return tableService.deleteInstance(tableName, primaryKeyValue);
    }

}
