package com.talan.AdminModule.service;

import com.talan.AdminModule.config.DatabaseInitializer;
import com.talan.AdminModule.dto.*;
import com.talan.AdminModule.dto.ColumnInfo;
import com.talan.AdminModule.entity.ParamAudit;
import com.talan.AdminModule.repository.ParamAuditRepository;
import jakarta.annotation.PostConstruct;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@DependsOn("databaseInitializer")
public class ParamTableService {
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
    private final List<DeleteRequest> deleteRequests =new ArrayList<>();
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
            TableInfo originalTable = allTablesWithColumns.getAllTablesWithColumns().get(i);
            List<ColumnInfo> columns = originalTable.getColumns();
            List<ColumnInfo> filteredColumns =  columns.stream().filter(column->!column.getName().equals(ACTIVE)).toList();

            TableInfo filteredTable = new TableInfo(originalTable.getName(),originalTable.getType(),originalTable.getPk(), originalTable.getTotalRows(), filteredColumns);
            paginatedTables.add(filteredTable);
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
    public DataFromTable getDataFromTable(String tableName, TableDataRequest request) {
        StringBuilder sqlQuery = buildSqlQuery(tableName, request);
        LOGGER.debug("Executing SQL: {}", sqlQuery);
        DataFromTable dataFromTable =new DataFromTable();
List<String> deletedRequestsData =new ArrayList<>();
List<String> updatedRequestsData =new ArrayList<>();
        dataFromTable.setData (jdbcTemplate.queryForList(String.valueOf(sqlQuery)));


        for (Map<String, Object> row : dataFromTable.getData()) {
            String primaryKeyValue = row.get(primaryKeyDetails(tableName).getName()).toString();
            for (DeleteRequest deleteRequest : getdeleterequestByTable(tableName)) {
                if (primaryKeyValue.equals(deleteRequest.getPrimaryKeyValue())) {
                    deletedRequestsData.add(deleteRequest.getPrimaryKeyValue());
                }
            }
            for (UpdateRequest updateRequest : getupdaterequestByTable(tableName)) {
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
                    .map(column -> "LOWER ( CAST ("+column+" AS TEXT ) )" + " LIKE '%"+request.getSearch()+"%'")
                    .collect(Collectors.joining(" OR ")));
            sqlQuery.append(")");
        }

        sqlQuery.append(orderByClause(tableName, request))
                .append(limitOffsetClause(request));
        return sqlQuery;
    }

    //STEP 1 SQLREQUEST FETCH DATA SELECT IF COLUMNS EMPTY APPEND ALL SELECTCOLUMNS
    public StringBuilder buildSelectClause(String tableName, TableDataRequest request) {
        StringBuilder selectClause = new StringBuilder("SELECT ");
        List<String> columns = getAllColumns(tableName).stream().filter(Objects::nonNull)
                .map(ColumnInfo::getName)
                .filter(column -> !column.contains(ACTIVE))
                .toList();

        if (request.getColumns().isEmpty()) {
            appendAllColumns(selectClause, columns);
        } else {
            appendSpecifiedColumns(selectClause, columns, request);
        }
        if (selectClause.charAt(selectClause.length() - 1) == ' ') {
            selectClause.delete(selectClause.length() - 2, selectClause.length());
        }
        return selectClause;

    }

    public void appendAllColumns(StringBuilder selectClause, List<String> columns) {
        for (String column : columns) {
            selectClause.append("CAST (").append(column).append(" AS TEXT )").append(", ");
        }
    }

    public void appendSpecifiedColumns(StringBuilder selectClause, List<String> columns, TableDataRequest request) {
        for (String column : request.getColumns()) {
            if (columns.contains(column)) {
                selectClause.append("CAST (").append(column).append(" AS TEXT )").append(", ");
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




    //ADD ITERATION COLUMNS M STRUCTURE IF COLUMN ==key f instance data  affectation w !primaryKey
    // ==> convert type instance datavalue l column type +column+ placeholder l vel value +value
    public ResponseDto addInstance(Map<String, String> instanceData, String tableName) {
        List<ColumnInfo> allColumns = getAllColumns(tableName);
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<Object> params = new ArrayList<>();

        for (ColumnInfo column : allColumns) {
            if (!column.getName().equalsIgnoreCase(primaryKeyDetails(tableName).getName())) {
                String dataValue = instanceData.get(column.getName());

                params.add(convertToDataType(dataValue, column.getType()));
                columns.append(column.getName()).append(",");
                values.append("?,");
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

        ParamAudit audit = ParamAudit.constructForInsertion(tableName, "ADDED", version, instanceData.toString(), username);
        paramAuditRepository.save(audit);
ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess("Record added successfully");
        return responseDto;
    }

    public String getUsernameFromSecurityContext() {
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        return requestWrapper.getRemoteUser();
    }


//LIST SCHEDULED FOR UPDATE WITH SIMULATION TOC CHECK REQUEST VALIDITY
public ResponseDto addupdaterequest(UpdateRequest updateRequest) {
    ResponseDto responseDto = new ResponseDto();

    boolean exists = updateRequests.stream()
            .anyMatch(req -> req.getTableName().equals(updateRequest.getTableName()) &&
                    req.getInstanceData().equals(updateRequest.getInstanceData()));
    if (!exists) {
        if (simulateUpdate(updateRequest)) {

                updateRequests.add(updateRequest);

            responseDto.setSuccess("Update request validated and added successfully.");
        } else {
            responseDto.setError("Validation failed; update request not added.");
        }
    } else {
        responseDto.setError("Update request already exists.");
    }

    return responseDto;
}
    public ResponseDto cancelupdaterequest(String primaryKeyValue ,String tableName) {
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
                    responseDto.setSuccess("Update of " + primaryKey + " cancelled successfully");
                } else {
                    responseDto.setError("Update of " + primaryKey + " not cancelled");
                }
                requestFound = true;
                break;
            }
        }
        if (!requestFound) {
            responseDto.setError("Request not found: " + primaryKey);
        }

        return responseDto;
    }
  public  List<UpdateRequest> getupdaterequestByTable(String tableName){
        return updateRequests.stream()
                .filter(updateRequest -> updateRequest.getTableName().equals(tableName)).toList();
    }
   public List<DeleteRequest> getdeleterequestByTable(String tableName){
        return deleteRequests.stream()
                .filter(deleteRequest -> deleteRequest.getTableName().equals(tableName)).toList();
    }
        public ResponseDto updateInstance (UpdateRequest updateRequest,Integer version){
            ResponseDto responseDto=new ResponseDto();

                List<ColumnInfo> allColumns = getAllColumns(updateRequest.getTableName());
                StringBuilder setClause = new StringBuilder();
                List<Object> params = new ArrayList<>();

                String primaryKeyColumn = primaryKeyDetails(updateRequest.getTableName()).getName();


                Object primaryKeyValue = null;
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
                int rowsUpdated = jdbcTemplate.update("UPDATE " + updateRequest.getTableName() + " SET " + setClause + " WHERE " + primaryKeyColumn + " = " + primaryKeyValue, params.toArray());
                if (rowsUpdated > 0) {
                    responseDto.setSuccess("Instance updated successfully");

                    assert primaryKeyValue != null;
                    ParamAudit paramAudit = ParamAudit.constructForUpdate(updateRequest.getTableName(),primaryKeyValue.toString(),updateRequest.getInstanceData().toString(),"EDITED",version, updateRequest.getUsername());
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
try { ResponseDto responseDto=updateInstance(updateRequest,version);
    if (responseDto.getSuccess()!=null){
        result=true;}
    return result;
    }   finally {
        transactionManager.rollback(status);
        }
    }

    public ResponseDto addeleterequest(DeleteRequest deleteRequest) {
        ResponseDto responseDto = new ResponseDto();
        if (deleteRequests.stream() .noneMatch(req ->
                req.getTableName().equals(deleteRequest.getTableName()) &&
                        req.getPrimaryKeyValue().equals(deleteRequest.getPrimaryKeyValue()))&&simulateDelete(deleteRequest)){
            deleteRequests.add(deleteRequest);
            responseDto.setSuccess("INSTANCE ADDED TO DELETE 8 AM");

        }
        else {
            responseDto.setError("INSTANCE ALREADY EXISTS");
        }
        return responseDto ;
    }
    public ResponseDto canceldeleterequest(String tableName,String primaryKeyValue) {
        ResponseDto responseDto = new ResponseDto();
        boolean requestFound = false;

        for (DeleteRequest delete : deleteRequests) {
            if (delete.getTableName().equals(tableName) &&
                    delete.getPrimaryKeyValue().equals(primaryKeyValue)) {
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
        if (!requestFound) {
            responseDto.setError("Request not found: " + primaryKeyValue);
        }

        return responseDto;
    }
    @Transactional
    @Scheduled(cron = "0 56 21 * * *")
    public  ResponseDto executeupdate() {
        ResponseDto responseDto = new ResponseDto();
        List<String> uniqueTableNames = new ArrayList<>();
        for (UpdateRequest updateRequest : updateRequests) {
            if (!uniqueTableNames.contains(updateRequest.getTableName())) {
                uniqueTableNames.add(updateRequest.getTableName());
            }
        }

        for (String uniqueTableName : uniqueTableNames) {
            List<UpdateRequest> tableUpdateRequests = getupdaterequestByTable(uniqueTableName);
            Integer version = findMaxVersionByTableName(uniqueTableName) + 1;
            for (UpdateRequest tableupdateRequest:tableUpdateRequests){

               responseDto= updateInstance(tableupdateRequest,version);
               if (responseDto.getSuccess()!=null){
                   updateRequests.remove(tableupdateRequest);
               }
               else break;
            }

        }
        return responseDto;
    }
    @Transactional
   @Scheduled(cron = "0 56 21 * * *")
    public ResponseDto executeDeletion () {
            ResponseDto responseDto = new ResponseDto();

                List<String> uniqueTableNames = new ArrayList<>();
                for (DeleteRequest deleteRequest : deleteRequests) {
                    if (!uniqueTableNames.contains(deleteRequest.getTableName())) {
                        uniqueTableNames.add(deleteRequest.getTableName());
                    }
                }
               for (String uniqueTableName : uniqueTableNames){
                List <DeleteRequest>tableDeletedRequests =getdeleterequestByTable(uniqueTableName);
                Integer version=findMaxVersionByTableName(uniqueTableName)+1;

                 for (DeleteRequest tableDeletedRequest:tableDeletedRequests){
                     responseDto = deleteInstance(tableDeletedRequest,version);

                }}

            return responseDto;
        }
   public ResponseDto deleteInstance(DeleteRequest deleteRequest,Integer version){
       ResponseDto responseDto = new ResponseDto();
        int rowsUpdated = 0;
       String inputValue = deleteRequest.getPrimaryKeyValue();
       String primaryKeyColumn = primaryKeyDetails(deleteRequest.getTableName()).getName();
       String primaryKeyColumnType = primaryKeyDetails(deleteRequest.getTableName()).getType();
       Object primaryKeyValue = convertToDataType(inputValue, primaryKeyColumnType);
       String sqlQuery = "UPDATE " + deleteRequest.getTableName() + " SET active = false WHERE " + primaryKeyColumn + " = ?";
       rowsUpdated =jdbcTemplate.update(sqlQuery, primaryKeyValue);

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
        public Object convertToDataType (String inputValue, String columnType){

            Object convertedValue;

                convertedValue = switch (columnType.toLowerCase()) {
                    case "int8", "bigint", "bigserial", "serial" -> Long.parseLong(inputValue);
                    case "int", "integer", "smallint" -> Integer.parseInt(inputValue);
                    case "varchar", "text", "bpchar" -> inputValue;
                    case "timestamptz" -> {
                        LocalDateTime dateTime;
                        if (inputValue.isEmpty()) {
                            dateTime = LocalDateTime.now();
                        } else {
                            dateTime = LocalDateTime.parse(inputValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        }
                        yield Timestamp.valueOf(dateTime);
                    }

                    default -> inputValue;
                };

            return convertedValue;
        }

    public ColumnInfo primaryKeyDetails(String tableName) {
        return allTablesWithColumns.getAllTablesWithColumns().stream()
                .filter(table -> table.getName().equals(tableName))
                .findFirst()
                .map(TableInfo::getPk)
                .orElse(null);
    }
        public List<ParamAudit> paramHistory(String tableName){
        return paramAuditRepository.findByTableName(tableName);
        }

    }
