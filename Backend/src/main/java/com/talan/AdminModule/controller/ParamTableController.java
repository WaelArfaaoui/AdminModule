package com.talan.AdminModule.controller;

import com.talan.AdminModule.dto.*;
import com.talan.AdminModule.entity.ParamAudit;
import com.talan.AdminModule.service.ParamTableService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/api/tables")
@RestController
public class ParamTableController {
    @Autowired
    private ParamTableService tableService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/{limit}/{offset}")
    public TablesWithColumns retrieveAllTablesAndColumns(@PathVariable int limit, @PathVariable int offset) {
        return tableService.retrieveAllTablesWithFilteredColumns(limit,offset);
    }

    @GetMapping("/{tableName}")
    public DataFromTable getDataFromTable(
            @PathVariable String tableName,
            @ModelAttribute TableDataRequest request
    ) {
        return tableService.getDataFromTable(
                tableName,
                request
        );
    }
    @PatchMapping("/cancelupdate/{tableName}/{primaryKeyValue}")
    public ResponseDto cancelupdaterequest(@PathVariable String primaryKeyValue,
                                           @PathVariable String tableName){
        return tableService.cancelupdaterequest(primaryKeyValue,tableName);
    }


   @PutMapping("/update/{tableName}")
   public ResponseDto updateInstance(@RequestBody Map<String, String> instanceData,
                                     @PathVariable String tableName) {
       SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
       String username = requestWrapper.getRemoteUser();
       UpdateRequest updateRequest = new UpdateRequest(instanceData,tableName,username);

       return tableService.addupdaterequest(updateRequest);

   }
    @PostMapping("/{tableName}")
    public ResponseDto addInstance(@RequestBody Map<String, String> instanceData,
                                   @PathVariable("tableName") String tableName) {

        return tableService.addInstance(instanceData, tableName);

    }
@PatchMapping("/{tableName}/delete/{primaryKeyValue}")
public ResponseDto deleteRecord(@PathVariable String tableName, @PathVariable String primaryKeyValue) {
    SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
    String username = requestWrapper.getRemoteUser();
    DeleteRequest deleteRequest = new DeleteRequest(tableName, primaryKeyValue,username);

    return tableService.addeleterequest(deleteRequest);

}
    @PatchMapping("/{tableName}/canceldeletion/{primaryKeyValue}")
public ResponseDto canceldeleterequest(@PathVariable String tableName, @PathVariable String primaryKeyValue){
        return tableService.canceldeleterequest(tableName,primaryKeyValue);
    }
    @GetMapping("/{tableName}/history")
       public List<ParamAudit> paramHistory(@PathVariable String tableName){
        return tableService.paramHistory(tableName);
    }

  /*  @GetMapping("/search")
    public List<Map<String,String>> dynamicSearch(@RequestBody SearchRequest searchRequest){
        return tableService.dynamicSearch(searchRequest);
    }*/
        }
