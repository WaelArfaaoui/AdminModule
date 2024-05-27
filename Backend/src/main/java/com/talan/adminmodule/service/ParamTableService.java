package com.talan.adminmodule.service;

import com.talan.adminmodule.config.DatabaseInitializer;
import com.talan.adminmodule.dto.*;
import com.talan.adminmodule.dto.ColumnInfo;
import com.talan.adminmodule.entity.ParamAudit;
import com.talan.adminmodule.repository.ParamAuditRepository;
import jakarta.annotation.PostConstruct;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.postgresql.jdbc.PgArray;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class ParamTableService  {
@Autowired
private JdbcTemplate jdbcTemplate;
    @Autowired
    private DatabaseInitializer databaseInitializer;
    @Autowired
    private ParamAuditRepository paramAuditRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PlatformTransactionManager transactionManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamTableService.class);

    private TablesWithColumns allTablesWithColumns =new TablesWithColumns();

    public static final String ACTIVE ="active";
    List<UpdateRequest>updateRequests = new ArrayList<>();
     List<DeleteRequest> deleteRequests =new ArrayList<>();
    @PostConstruct
    public void initialize() {
        allTablesWithColumns = databaseInitializer.getAllTablesWithColumns();
    }
    public ParamTableService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//Tjib l tablesNumber mel allTablesWithColumns f initializer fiha structure DB baaed iteration fel filtered l request hedhi
    // + filtre column ACTIVE t set listcolumns jdida f TableInfo
    public TablesWithColumns retrieveAllTablesWithFilteredColumns(int limit, int offset) {
        List<TableInfo> paginatedTables = new ArrayList<>();
        TablesWithColumns tablesWithColumns = new TablesWithColumns();
        int endIndex = (int) Math.min(allTablesWithColumns.getNumberTables(), offset + (long)limit);


        for (int i = offset; i < endIndex; i++) {
            TableInfo paramTable = allTablesWithColumns.getAllTablesWithColumns().get(i);
            List<ColumnInfo> columns = paramTable.getColumns();
            List<ColumnInfo> filteredColumns =  columns.stream().filter(column->!column.getName().equals(ACTIVE)).toList();
            paramTable.setColumns(filteredColumns);
            paginatedTables.add(paramTable);
        }
        tablesWithColumns.setAllTablesWithColumns(paginatedTables);
        tablesWithColumns.setNumberTables(allTablesWithColumns.getNumberTables());
        return tablesWithColumns;
    }

// ALL COLUMNS BEL ACTIVE !!!
    public List<ColumnInfo> getAllColumns(String tableName) {
        TableInfo tableInfoOptional=allTablesWithColumns.getAllTablesWithColumns().stream()
                .filter(table -> table.getName().equals(tableName))
                .findFirst().orElse(null);
        List<ColumnInfo> alltablecolumns=new ArrayList<>();
        if (tableInfoOptional!=null){
            alltablecolumns  = tableInfoOptional.getColumns();
        }
       return alltablecolumns;
    }

    //BUILD l query w baaed executi w l map eli bech return KOL ROW converted to string aala le types par exemple table (NO TOSTRING)
    // iteration aal list updateRequest w DelteRequest ken fama idrequete== rowid (scheduled for deletion/edition)
    public DataFromTable getDataFromTable(String tableName,TableDataRequest request) {
        StringBuilder sqlQuery = buildSqlQuery(tableName, request);
        LOGGER.debug("Executing SQL: {}", sqlQuery);
        DataFromTable dataFromTable =new DataFromTable();
        List<String> deletedRequestsData =new ArrayList<>();
        List<String> updatedRequestsData =new ArrayList<>();
        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(sqlQuery.toString());
        Optional< TableInfo> tab = allTablesWithColumns.getAllTablesWithColumns().stream().filter(tableInfo -> tableInfo.getName().equalsIgnoreCase(tableName)).findFirst();
        if (tab.isPresent()){
            TableInfo table= tab.get();
            Optional< ColumnInfo> column=  table.getColumns().stream().filter(columnInfo -> columnInfo.getType().startsWith("_")).findFirst();
            if (column.isPresent()){
                for (Map<String, Object> row : queryResult) {
                    if (row.containsKey(column.get().getName())) {
                        Object columnValue = row.get(column.get().getName());
                        if (columnValue instanceof PgArray) {
                            row.put(column.get().getName(), columnValue.toString());
                        }
                    }
                }
            }
        }

        dataFromTable.setData(queryResult);
//     dataFromTable.setData (jdbcTemplate.queryForList(sqlQuery.toString()));
        for (Map<String, Object> row : dataFromTable.getData()) {
            String primaryKeyValue = row.get(primaryKeyDetails(tableName).getName()).toString();
            for (DeleteRequest deleteRequest : getDeleteRequestByTable(tableName)) {
                if (primaryKeyValue.equals(deleteRequest.getPrimaryKeyValue())) {
                    deletedRequestsData.add(deleteRequest.getPrimaryKeyValue());
                }
            }
            for (UpdateRequest updateRequest : getUpdateRequestByTable(tableName)) {
                if (primaryKeyValue.equals(updateRequest.getInstanceData().get(primaryKeyDetails(tableName).getName()))) {
                    updatedRequestsData.add(updateRequest.getInstanceData().get(primaryKeyDetails(tableName).getName()));
                }
            }
        }
        dataFromTable.setDeleteRequests(deletedRequestsData);
        dataFromTable.setUpdateRequests(updatedRequestsData);
        return  dataFromTable;

    }
    public StringBuilder buildSqlQuery(String tableName, TableDataRequest request) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(buildSelectClause(tableName, request))
                .append(" FROM ").append(tableName)
                .append(" WHERE ").append(tableName).append(".active = 'true'");

        if (request.getSearch() != null && !request.getSearch().isEmpty() && !request.getSearch().equals("undefined") && !request.getColumns().isEmpty()) {
            sqlQuery.append(" AND (")
                     .append(request.getColumns().stream()
                    .map(column -> "LOWER ( CAST ("+column+" AS TEXT ) )" + " LIKE  '"+request.getSearch().toLowerCase()+"%'")
                    .collect(Collectors.joining(" OR ")));
            sqlQuery.append(")");
        }

        sqlQuery.append(orderByClause(tableName, request))
                .append(limitOffsetClause(request));
        return sqlQuery;
    }
    public StringBuilder buildSelectClause(String tableName, TableDataRequest request) {
        StringBuilder selectClause = new StringBuilder("SELECT ");
        List<String> columns = getAllColumns(tableName).stream().filter(Objects::nonNull)
                .map(ColumnInfo::getName)
                .filter(column -> !column.contains(ACTIVE))
                .toList();
        String pk = primaryKeyDetails(tableName).getName();
        if (request.getColumns().isEmpty()) {
            appendAllColumns(selectClause, columns);
        } else {
            appendSpecifiedColumns(selectClause, columns,pk, request);
        }
        if (selectClause.charAt(selectClause.length() - 1) == ' ') {
            selectClause.delete(selectClause.length() - 2, selectClause.length());
        }
        return selectClause;

    }
    public void appendAllColumns(StringBuilder selectClause, List<String> columns) {
        for (String column : columns) {
            selectClause.append(column).append(", ");

        }

    }
    public void appendSpecifiedColumns(StringBuilder selectClause, List<String> columns,String pk, TableDataRequest request) {
        selectClause.append(pk).append(", ");
        for (String column : request.getColumns()) {
            if (columns.contains(column)&& !column.equalsIgnoreCase(pk)) {
                selectClause.append(column).append(", ");
            }
        }
    }
    public String orderByClause(String tableName, TableDataRequest request) {
        String sortByColumn = request.getSortByColumn() != null && !request.getSortByColumn().equals("null") && !request.getSortByColumn().isEmpty()
                ? request.getSortByColumn()
                : primaryKeyDetails(tableName).getName();
        String order= request.getSortOrder()!=null &&!request.getSortOrder().equals("null") &&!request.getSortOrder().isEmpty()&& sortByColumn.equals(request.getSortByColumn()) ? request.getSortOrder() :" " ;
        return " ORDER BY " + sortByColumn +" "+order;
    }
    public String limitOffsetClause(TableDataRequest request) {
        StringBuilder limitOffsetClause = new StringBuilder();
        if (request.getLimit() > 0) {
            limitOffsetClause.append(" LIMIT ").append(request.getLimit());
            if (request.getOffset() > 0) {
                limitOffsetClause.append(" OFFSET ").append(request.getOffset());
            }
        }
        return limitOffsetClause.toString();
    }

    public ResponseDto addInstance(Map<String, String> instanceData, String tableName) {
        List<ColumnInfo> allColumns = getAllColumns(tableName).stream().filter(columnInfo -> !columnInfo.getName().equalsIgnoreCase(ACTIVE)).toList();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<Object> params = new ArrayList<>();
        String primaryKeyName = primaryKeyDetails(tableName).getName();
        String primaryKeyType = primaryKeyDetails(tableName).getType();
        for (ColumnInfo column : allColumns) {
            String columnName = column.getName();


            if (!columnName.equalsIgnoreCase(primaryKeyName) ||
                    (columnName.equalsIgnoreCase(primaryKeyName) &&
                            !(primaryKeyType.equalsIgnoreCase("serial") || primaryKeyType.equalsIgnoreCase("bigserial")))) {

                String dataValue = instanceData.get(columnName);

                params.add(convertToDataType(dataValue, column.getType()));
String type =column.getType();
                columns.append(columnName).append(",");
                if (type.equalsIgnoreCase("varchar") || type.equalsIgnoreCase("text") || type.equalsIgnoreCase("bpchar") ) {
                    values.append("?,");
                }
                else {
                    values.append(" ?,");
                }
            }
        }


        if (!columns.toString().isEmpty()) {
            columns.deleteCharAt(columns.length() - 1);
            values.deleteCharAt(values.length() - 1);
        }
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
        jdbcTemplate.update(sql, params.toArray());
        String username = getUsernameFromSecurityContext();
        Integer version = findMaxVersionByTableName(tableName) + 1;
        Map<String, String> cleanmap = instanceData.entrySet().stream().filter(value -> !(value.getValue().isEmpty())&&!(value.getValue().equals("undefined"))) .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        String auditRow = cleanmap.toString().substring(1, cleanmap.toString().length()-1);
        ParamAudit audit = ParamAudit.constructForInsertion(tableName, "ADDED", version,auditRow, username);
        paramAuditRepository.save(audit);
ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess("Record added successfully");
        return responseDto;
    }

    public String getUsernameFromSecurityContext() {
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        return requestWrapper.getRemoteUser();
    }


public ResponseDto addUpdateRequest(UpdateRequest updateRequest) {
    ResponseDto responseDto = new ResponseDto();

    boolean exists = updateRequests.stream()
            .anyMatch(req -> req.getTableName().equals(updateRequest.getTableName()) &&
                    req.getInstanceData().equals(updateRequest.getInstanceData()));
    if (!exists) {
        if (simulateUpdate(updateRequest)) {

                updateRequests.add(updateRequest);

            responseDto.setSuccess("Update request validated and added successfully.");
        } else {
            responseDto.setError("Validation failed, update request not added.");
        }
    } else {
        responseDto.setError("Update request already exists.");
    }

    return responseDto;
}
    public ResponseDto cancelUpdateRequest(String primaryKeyValue , String tableName) {
        ResponseDto responseDto = new ResponseDto();
         String primaryKey= primaryKeyDetails(tableName).getName();
        boolean requestFound = false;
        for (UpdateRequest update : updateRequests) {
            if (update.getTableName().equals(tableName) &&
                    update.getInstanceData().get(primaryKey).equals(primaryKeyValue)) {
                int size = updateRequests.size();
                updateRequests.remove(update);
                int newsize = updateRequests.size();

                if (size != newsize) {
                    responseDto.setSuccess("Update of " + primaryKeyValue + " cancelled successfully");
                } else {
                    responseDto.setError("Update of " + primaryKeyValue + " not cancelled");
                }
                requestFound = true;
                break;
            }
        }
        if (!requestFound) {
            responseDto.setError("Request not found: " + primaryKeyValue);
        }

        return responseDto;
    }
  public  List<UpdateRequest> getUpdateRequestByTable(String tableName){
        return updateRequests.stream()
                .filter(updateRequest -> updateRequest.getTableName().equals(tableName)).toList();
    }
   public List<DeleteRequest> getDeleteRequestByTable(String tableName){
        return deleteRequests.stream()
                .filter(deleteRequest -> deleteRequest.getTableName().equals(tableName)).toList();
    }
    public ResponseDto updateInstance (UpdateRequest updateRequest,Integer version){
        ResponseDto responseDto=new ResponseDto();

        List<ColumnInfo> allColumns = getAllColumns(updateRequest.getTableName());
        StringBuilder setClause = new StringBuilder();
        StringBuilder sqlQuery = new StringBuilder();

        List<Object> params = new ArrayList<>();

        String primaryKeyColumn = primaryKeyDetails(updateRequest.getTableName()).getName();
        sqlQuery.append("UPDATE ")
                .append(updateRequest.getTableName()) ;


        Object primaryKeyValue = new Object();
        for (ColumnInfo columnMap : allColumns) {
            String columnName = columnMap.getName();
            String columnType = columnMap.getType();

            if (updateRequest.getInstanceData().containsKey(columnName)) {
                Object convertedValue = convertToDataType(updateRequest.getInstanceData().get(columnName), columnType);


                if (!columnName.equals(primaryKeyColumn)) {
                    setClause.append(columnName).append(" = ?, ");
                    params.add(convertedValue);
                } else {
                    primaryKeyValue = convertedValue;
                }
            }
        }
        setClause.delete(setClause.length() - 2, setClause.length());
        sqlQuery.append(" SET ")
                .append(setClause)
                .append(" WHERE ")
                .append(primaryKeyColumn);
        if (primaryKeyValue!=null){
            sqlQuery.append(" = ?");
                    params.add(primaryKeyValue);
        }

        int rowsUpdated = jdbcTemplate.update(sqlQuery.toString(), params.toArray());
        if (rowsUpdated > 0 && primaryKeyValue!=null) {
            responseDto.setSuccess("Instance updated successfully");
            Map<String, String> cleanmap = updateRequest.getInstanceData().entrySet().stream().filter(value -> !(value.getValue().isEmpty())&&!(value.getValue().equals("undefined"))) .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            String auditRow = cleanmap.toString().substring(1, cleanmap.toString().length()-1);
            ParamAudit paramAudit = ParamAudit.constructForUpdate(updateRequest.getTableName(), primaryKeyValue.toString(), auditRow, "EDITED", version, updateRequest.getUsername());

            paramAuditRepository.save(paramAudit);

        } else {
            responseDto.setError( "message No records updated");
        }
        return responseDto;
    }
        public boolean simulateDelete(DeleteRequest request){
        boolean result=false;
        Integer version =5;
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setName("simulateDeleteTransaction");
            TransactionStatus status = transactionManager.getTransaction(def);
            try { ResponseDto responseDto=deleteInstance(request,version);
                if (responseDto.getSuccess()!=null){
                    result=true;}
                return result;
            }   finally {
                transactionManager.rollback(status);
            }
        }
    public boolean simulateUpdate(UpdateRequest updateRequest) {
        boolean result=false;
        Integer version=5;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        def.setName("simulateUpdateTransaction");

        TransactionStatus status = transactionManager.getTransaction(def);
    try {
    ResponseDto responseDto=updateInstance(updateRequest,version);
    if (responseDto.getSuccess()!=null){
        result=true;}
    return result;
    }   finally {
        transactionManager.rollback(status);
        }
    }

    public List<ForeignKeyOption> foreignKeyoptions(String tableName) {
        List<ForeignKeyOption> result = new ArrayList<>();
        List<ForeignKey> fks = allTablesWithColumns.getAllforeignKeys().stream()
                .filter(fk -> fk.getFkTableName().equals(tableName))
                .toList();

        for (ForeignKey fk : fks) {
            List<Map<String, Object>> options = jdbcTemplate.queryForList(
                    "SELECT " + fk.getReferencedColumn() + " FROM " + fk.getReferencedTable() + " WHERE active = 'true'");
            ForeignKeyOption ref = new ForeignKeyOption();
            ref.setColumn(fk.getFkColumnName());

            List<String> refoptions = new ArrayList<>();
            for (Map<String, Object> option : options) {
                refoptions.add(option.get(fk.getReferencedColumn()).toString());
            }
            ref.setOptions(refoptions);
            result.add(ref);
        }
        return result;
    }

    public ResponseDto addDeleteRequest(DeleteRequest deleteRequest) {
        ResponseDto responseDto = new ResponseDto();
        if (deleteRequests.stream().noneMatch(req ->
                req.getTableName().equals(deleteRequest.getTableName()) &&
                        req.getPrimaryKeyValue().equals(deleteRequest.getPrimaryKeyValue()))&&simulateDelete(deleteRequest)){
            deleteRequest.setUssername(getUsernameFromSecurityContext());
            deleteRequests.add(deleteRequest);
            responseDto.setSuccess("INSTANCE ADDED TO DELETE 8 AM");
        }
        else {
            responseDto.setError("INSTANCE ALREADY EXISTS");
        }
        return responseDto ;
    }
    @Transactional
    public ResponseDto deletecascade(DeleteRequest deleteRequest){
        List<DeleteRequest> response = new ArrayList<>();
        response.add(deleteRequest);
        checkReferencedRecursive(deleteRequest, response);
        for (DeleteRequest del : response){
            addDeleteRequest(del);
        }
        ResponseDto result = new ResponseDto();
        result.setSuccess("Cascade deletion succeeded");
        return result;
    }

    public TreeMapData tablesforDashboard(){
        TreeMapData data = new TreeMapData();
        data.setData(paramAuditRepository.paramTablesTreeMap());
        data.setNumberupdates(paramAuditRepository.count());
        return data;
    }
    private void checkReferencedRecursive(DeleteRequest deleteRequest, List<DeleteRequest> response) {
        List<ForeignKey> fks = allTablesWithColumns.getAllforeignKeys().stream().filter( fk -> fk.getReferencedTable().equals(deleteRequest.getTableName())).toList();
        ColumnInfo pk = primaryKeyDetails(deleteRequest.getTableName());
        String typePk = pk.getType();
        Object pkValue = convertToDataType(deleteRequest.getPrimaryKeyValue(),typePk);
        String sql="SELECT DISTINCT  * FROM "+ deleteRequest.getTableName() + " WHERE "+pk.getName() +" = ? ";
        Map<String,Object> row = jdbcTemplate.queryForMap(sql,pkValue);
        for (ForeignKey fk:fks){
            Object fkValue= row.get(fk.getReferencedColumn());
            List<Map<String,Object>>rowref = jdbcTemplate.queryForList("SELECT  "+ primaryKeyDetails(fk.getFkTableName()).getName()+" FROM "+fk.getFkTableName() + " WHERE "+fk.getFkColumnName() +" = "+ fkValue);
            if (!rowref.isEmpty()) {
                List<String> occurences = rowref.stream().map(map -> map.get(primaryKeyDetails(fk.getFkTableName()).getName()).toString()).collect(Collectors.toList());
        for (String refId :occurences) {
         DeleteRequest deleteRequest1 = new DeleteRequest(fk.getFkTableName(), refId);

          response.add(deleteRequest1);
           }
                for (String primaryKeyValue : occurences) {
                    DeleteRequest childDeleteRequest = new DeleteRequest(fk.getFkTableName(), primaryKeyValue);
                    checkReferencedRecursive(childDeleteRequest, response);
                }
            }
        }
    }
    public Boolean checkunicity(String primaryKeyValue ,String tableName){
      String pkType  =primaryKeyDetails(tableName).getType();
      Object convertedValue = convertToDataType(primaryKeyValue,pkType);
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + primaryKeyDetails(tableName).getName() + " = ?";
//      List<Object> params = new ArrayList<>();
//
//        params.add(primaryKeyDetails(tableName).getName());
//        params.add(convertedValue);
        Object[] params = new Object[] { convertedValue };

        Integer count = jdbcTemplate.queryForObject(sql,params, Integer.class);

    return count ==0 ;}

    public List<DeleteRequest> checkReferenced(DeleteRequest deleteRequest){
      List<DeleteRequest>   response = new ArrayList<>();
     List<ForeignKey> fks = allTablesWithColumns.getAllforeignKeys().stream().filter( fk -> fk.getReferencedTable().equals(deleteRequest.getTableName())).toList();
      ColumnInfo pk = primaryKeyDetails(deleteRequest.getTableName());
      String typePk = pk.getType();
      Object pkValue = convertToDataType(deleteRequest.getPrimaryKeyValue(),typePk);
        String sql = "SELECT * FROM " + deleteRequest.getTableName() + " WHERE " +pk.getName() + " = ?";
        Map<String,Object> row = jdbcTemplate.queryForMap(sql,pkValue);


     for (ForeignKey fk:fks){
    Object fkValue= row.get(fk.getReferencedColumn());
    String querrysql ="SELECT DISTINCT "+ primaryKeyDetails(fk.getFkTableName()).getName()+" FROM "+fk.getFkTableName() + " WHERE "+fk.getFkColumnName() +" = ? ";
     List<Map<String,Object>>rowref = jdbcTemplate.queryForList(querrysql,fkValue);
         if (!rowref.isEmpty()) {
             List<String> occurences = rowref.stream().map(map -> map.get(primaryKeyDetails(fk.getFkTableName()).getName()).toString()).collect(Collectors.toList());

             DeleteRequest deleteRequest1 = new DeleteRequest(fk.getFkTableName(), occurences);
             response.add(deleteRequest1);

         }
     }
     return response;
    }
    @Transactional
    public ResponseDto cancelDeleteRequest(String tableName, String primaryKeyValue) {

        List<DeleteRequest> response = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto();
        boolean requestFound = false;
        DeleteRequest deleteRequest = new DeleteRequest(tableName,primaryKeyValue);
        checkReferencedRecursive(deleteRequest, response);
         response.add(deleteRequest);
        for (DeleteRequest del :response) {
        for (DeleteRequest delete : deleteRequests) {

    if (delete.getTableName().equals(del.getTableName()) &&
            delete.getPrimaryKeyValue().equals(del.getPrimaryKeyValue())) {
        int size = deleteRequests.size();
        deleteRequests.remove(delete);
        int newsize = deleteRequests.size();

        if (size != newsize) {
            responseDto.setSuccess("Deletion of " + primaryKeyValue + " cancelled successfully");
        } else {
            responseDto.setError("Deletion of " + primaryKeyValue + " not cancelled");
        }
        requestFound = true;
        break;
    }
}
        }
        if (!requestFound) {
            responseDto.setError("Request not found: " + primaryKeyValue);
        }

        return responseDto;
    }
    @Transactional
    @Scheduled(cron = "0 07 11 * * *")
    public void executeUpdate() {
        List<String> uniqueTableNames = new ArrayList<>();
        for (UpdateRequest updateRequest : updateRequests) {
            if (!uniqueTableNames.contains(updateRequest.getTableName())) {
                uniqueTableNames.add(updateRequest.getTableName());
            }
        }

        for (String uniqueTableName : uniqueTableNames) {
            List<UpdateRequest> tableUpdateRequests = getUpdateRequestByTable(uniqueTableName);
            Integer version = findMaxVersionByTableName(uniqueTableName) + 1;
            for (UpdateRequest tableupdateRequest : tableUpdateRequests) {

              ResponseDto  responseDto = updateInstance(tableupdateRequest, version);
                if (responseDto.getSuccess() != null) {
                    updateRequests.remove(tableupdateRequest);
                } else break;
            }
        }
    }
    @Transactional
   @Scheduled(cron = "0 07 11 * * *")
    public void executeDeletion () {
                List<String> uniqueTableNames = new ArrayList<>();
        for (DeleteRequest deleteRequest : deleteRequests) {
                    if (!uniqueTableNames.contains(deleteRequest.getTableName())) {
                        uniqueTableNames.add(deleteRequest.getTableName());
                    }
                }
               for (String uniqueTableName : uniqueTableNames){
                List <DeleteRequest>tableDeletedRequests = getDeleteRequestByTable(uniqueTableName);
                Integer version=findMaxVersionByTableName(uniqueTableName)+1;

                 for (DeleteRequest tableDeletedRequest:tableDeletedRequests){
                   ResponseDto responseDto = deleteInstance(tableDeletedRequest,version);
                   if (responseDto.getSuccess()!=null){
                       deleteRequests.remove(tableDeletedRequest);
                   }else break;

                }}


        }
   public ResponseDto deleteInstance(DeleteRequest deleteRequest,Integer version){
       ResponseDto responseDto = new ResponseDto();
        int rowsUpdated = 0;
       String inputValue = deleteRequest.getPrimaryKeyValue();
       String primaryKeyColumn = primaryKeyDetails(deleteRequest.getTableName()).getName();
       String primaryKeyColumnType = primaryKeyDetails(deleteRequest.getTableName()).getType();
       Object primaryKeyValue = convertToDataType(inputValue, primaryKeyColumnType);
       StringBuilder sqlQuery;
       sqlQuery = new StringBuilder();
       sqlQuery.append("UPDATE ")
               .append(deleteRequest.getTableName())
               .append(" SET active = false WHERE ")
               .append(primaryKeyColumn);

                  sqlQuery.append(" = ?");

       rowsUpdated =jdbcTemplate.update(sqlQuery.toString(), primaryKeyValue);

       if (rowsUpdated > 0) {
           responseDto.setSuccess("Record updated successfully ");
           deleteRequests.remove(deleteRequest);

           ParamAudit paramAudit = ParamAudit.constructForDeletion(deleteRequest.getTableName(),inputValue,"DELETED",version,deleteRequest.getUssername());
           paramAuditRepository.save(paramAudit);
       }
       return responseDto;
   }

         public Integer findMaxVersionByTableName(String tableName){
        return paramAuditRepository.findMaxVersionByTableName(tableName);
         }
    public Object convertToDataType(String inputValue, String columnType) {
        Object convertedValue;
        convertedValue = switch (columnType.toLowerCase()) {
            case "int8", "bigint" -> inputValue != null ? Long.parseLong(inputValue) : 0L;
            case "bigserial", "serial" -> inputValue != null ? Long.parseLong(inputValue) : null; // Return null for these types
            case "int", "int4", "integer", "int2" -> inputValue != null ? Integer.parseInt(inputValue) : 0;
            case "varchar", "text", "bpchar" -> inputValue;
            case "bool" -> Boolean.parseBoolean(inputValue);
            case "bytea" -> inputValue != null ? Base64.getDecoder().decode(inputValue) : null;
            case "timestamptz" -> {
                ZonedDateTime zonedDateTime;
                if (inputValue == null || inputValue.isEmpty() || inputValue.equalsIgnoreCase("null") || inputValue.equalsIgnoreCase("undefined")) {
                    zonedDateTime = ZonedDateTime.now();
                } else {
                    try {
                        zonedDateTime = ZonedDateTime.parse(inputValue);
                    } catch (Exception e) {
                        LocalDateTime dateTime = LocalDateTime.parse(inputValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
                    }
                }
                yield Timestamp.from(zonedDateTime.toInstant());
            }
            default -> inputValue;
        };
        return convertedValue;
    }



    public ColumnInfo primaryKeyDetails(String tableName) {
       Optional <ColumnInfo> optionalPk= allTablesWithColumns.getAllTablesWithColumns().stream()
                .filter(table -> table.getName().equals(tableName)).findFirst()
                .map(TableInfo::getPk);
        return optionalPk.orElseThrow(() -> new IllegalStateException("Primary key not found for table: " + tableName));

    }

        public List<ParamAudit> paramHistory(String tableName){
        return paramAuditRepository.findByTableName(tableName);
        }

    }
