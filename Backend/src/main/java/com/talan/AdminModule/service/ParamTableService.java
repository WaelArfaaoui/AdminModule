package com.talan.AdminModule.service;

import com.talan.AdminModule.dto.ColumnInfo;
import com.talan.AdminModule.dto.TableInfo;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class ParamTableService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public ParamTableService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public List<TableInfo> retrieveAllTablesWithColumns() {
        List<TableInfo> tablesWithColumnsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            // Get all tables
            ResultSet tables = metaData.getTables(null, "public", null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");

                // Get columns for each table
                List<ColumnInfo> columns = new ArrayList<>();
                ResultSet tableColumns = metaData.getColumns(null, "public", tableName, null);
                while (tableColumns.next()) {
                    String columnName = tableColumns.getString("COLUMN_NAME");

                        String columnType = tableColumns.getString("TYPE_NAME");
                        // You can add more column properties as needed
                        columns.add(new ColumnInfo(columnName, columnType));

                }

                Map<String, String> primaryKeyDetails = getPrimaryKeyColumnDetails(tableName);
                String primaryKeyColumnName = primaryKeyDetails.get("columnName");

                // Create a TableInfo object for the table with name, columns, and primary key details
                TableInfo tableInfo = new TableInfo();
                tableInfo.setName(tableName);
                tableInfo.setColumns(columns);
                tableInfo.setPk(primaryKeyColumnName);

                // Add the TableInfo object to the list
                tablesWithColumnsList.add(tableInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tablesWithColumnsList;
    }
    public List<TableInfo> retrieveAllTablesWithFilteredColumns() {
        List<TableInfo> allTablesWithColumns = retrieveAllTablesWithColumns();

        for (TableInfo tableInfo : allTablesWithColumns) {
            List<ColumnInfo> columns = tableInfo.getColumns();
            List<ColumnInfo> filteredColumns = new ArrayList<>();

            for (ColumnInfo column : columns) {
                if (!column.getName().equalsIgnoreCase("active")) {
                    // Add the column to the filtered columns list
                    filteredColumns.add(column);
                }
            }

            tableInfo.setColumns(filteredColumns);
        }

        return allTablesWithColumns;
    }


    public List<ColumnInfo> getAllColumns(String tableName) throws SQLException {
        List<TableInfo> tables = retrieveAllTablesWithColumns();
        for(TableInfo table : tables) {
            if(table.getName().equals(tableName)){
                return table.getColumns();
            }
        }

        return null;
    }



    public List<Map<String, Object>> getDataFromTable(String tableName, List<String> columns, String sortByColumn, String sortOrder, int limit, int offset) throws SQLException {
        StringBuilder sqlQuery = new StringBuilder("SELECT ");
        List<String> columnsall = new ArrayList<>();
          for (ColumnInfo column :getAllColumns(tableName)) {
              columnsall.add(column.getName());

          }
        if (columns.isEmpty()) {
            for (String column : columnsall) {
                if (!column.equals("active")) { // Exclude the 'active' column
                    sqlQuery.append(column).append(", ");
                }
            }
            sqlQuery.delete(sqlQuery.length() - 2, sqlQuery.length()); // Remove the last comma and space
        } else {
            // Construct the SELECT part of the SQL query with the specified columns
            for (String column : columns) {
                if (columnsall.contains(column) && !column.equals("active")) {
                    sqlQuery.append(column).append(", ");
                }
            }
        }

        // Remove the last comma and space if any columns are appended
        if (sqlQuery.charAt(sqlQuery.length() - 1) == ' ') {
            sqlQuery.delete(sqlQuery.length() - 2, sqlQuery.length());
        }

        // Add the FROM part of the SQL query
        sqlQuery.append(" FROM ").append(tableName);

        // Add condition for active records
        sqlQuery.append(" WHERE ").append(tableName).append(".active = true");

        // Add sorting if sortByColumn is provided
        if (sortByColumn != null && !sortByColumn.isEmpty()) {
            sqlQuery.append(" ORDER BY ").append(sortByColumn);
            // Add sort order if provided
            if (sortOrder != null && (sortOrder.equalsIgnoreCase("asc") || sortOrder.equalsIgnoreCase("desc"))) {
                sqlQuery.append(" ").append(sortOrder.toUpperCase());
            }
        }

        // Add limit and offset for pagination
        if (limit > 0) {
            sqlQuery.append(" LIMIT ").append(limit);
            if (offset > 0) {
                sqlQuery.append(" OFFSET ").append(offset);
            }
        }

        return jdbcTemplate.queryForList(sqlQuery.toString());
    }
    public Map<String, Object> addInstance(Map<String, String> instanceData, String tableName) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ColumnInfo> allColumns = getAllColumns(tableName);

            // Build INSERT query
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            List<Object> params = new ArrayList<>();

            allColumns.forEach(column -> {
                String columnName = column.getName();
                String columnType = column.getType();
                // Convertir le nom de la colonne en minuscules pour ignorer la casse
                String lowercaseColumnName = columnName.toLowerCase();

                for (String key : instanceData.keySet()) {
                    // Convertir la clé de instanceData en minuscules pour ignorer la casse
                    String lowercaseKey = key.toLowerCase();

                    // Comparer les noms de colonnes (en minuscules) avec les clés de instanceData (en minuscules)
                    if (lowercaseColumnName.equals(lowercaseKey)) {
                        String value = instanceData.get(key);
                        Object convertedValue = convertToDataType(value, columnType);

                        columns.append(columnName).append(",");
                        values.append("?,");
                        params.add(convertedValue);
                    }
                }
            });

            String insertQuery = "INSERT INTO " + tableName + " (" + columns.deleteCharAt(columns.length() - 1) +
                    ") VALUES (" + values.deleteCharAt(values.length() - 1) + ") RETURNING *";

            // Execute INSERT query
            List<Map<String, Object>> insertedData = jdbcTemplate.queryForList(insertQuery, params.toArray());

            result.put("message", "Instance added successfully");
            result.put("data", insertedData);

            return result;
        } catch (DataIntegrityViolationException e) {
            // Extract PostgreSQL error message from the exception
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof PSQLException pgSqlException) {
                // Get the main error message without details
                String errorMessage = pgSqlException.getMessage().split("\n")[0];
                // Create a map to hold the error message
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("message", errorMessage);
                return errorMap; // Return the PostgreSQL error message map
            } else {
                // Handle other types of data integrity violation exceptions
                e.printStackTrace();
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("message", "A data integrity violation occurred while processing the request.");
                return errorMap;
            }
        } catch (DataAccessException e) {
            // Handle other types of data access exceptions
            e.printStackTrace();
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "An error occurred while processing the request.");
            return errorMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Map<String, Object> updateInstance(Map<String, String> instanceData, String tableName) {
        Map<String, Object> result = new HashMap<>();
        try {
            // Fetch all columns for the table
            List<ColumnInfo> allColumns = getAllColumns(tableName);

            // Build UPDATE query
            StringBuilder setClause = new StringBuilder();
            List<Object> params = new ArrayList<>();
            Map<String, String> primaryKeyDetails = getPrimaryKeyColumnDetails(tableName);
            String primaryKeyColumn = primaryKeyDetails.get("columnName");

            // Iterate through instanceData and columns
            Object primaryKeyValue = null;
            for (ColumnInfo columnMap : allColumns) {
                String columnName = columnMap.getName();
                String columnType = columnMap.getType();

                // Check if instanceData contains the current column
                if (instanceData.containsKey(columnName)) {
                    Object convertedValue = convertToDataType(instanceData.get(columnName), columnType);

                    // Log converted values for debugging
                    System.out.println("Converted value for column " + columnName + ": " + convertedValue + " (" + columnType + ")");

                    if (!columnName.equals(primaryKeyColumn)) {
                        // Append column name and value placeholder to the set clause
                        setClause.append(columnName).append(" = ?, ");
                        params.add(convertedValue); // Add column values (except primary key) to parameters
                    } else {
                        // If the column is the primary key, add its value to the parameters list
                        primaryKeyValue = convertedValue;
                    }
                }
            }

            // Log the generated SET clause and parameters
            System.out.println("Generated SET clause: " + setClause.toString());
            System.out.println("Parameters: " + params.toString());

            // Remove the trailing comma and space from the set clause
            setClause.delete(setClause.length() - 2, setClause.length());

            // Construct the full UPDATE query with the primary key in the WHERE clause
            String updateQuery = "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKeyColumn + " = " + primaryKeyValue;

            // Log the generated UPDATE query
            System.out.println("Generated UPDATE query: " + updateQuery);

            // Execute the UPDATE query
            int rowsUpdated = jdbcTemplate.update(updateQuery, params.toArray());

            // Log the number of rows updated
            System.out.println("Rows updated: " + rowsUpdated);

            if (rowsUpdated > 0) {
                result.put("message", "Instance updated successfully");
            } else {
                result.put("message", "No records updated");
            }
        } catch (DataIntegrityViolationException e) {
            // Handle data integrity violation exceptions
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof PSQLException pgSqlException) {
                // Get the main error message without details
                String errorMessage = pgSqlException.getMessage().split("\n")[0];
                // Create a map to hold the error message
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("message", errorMessage);
                return errorMap; // Return the PostgreSQL error message map
            } else {
                // Handle other types of data integrity violation exceptions
                e.printStackTrace();
                result.put("message", "A data integrity violation occurred while processing the request.");
            }
        } catch (DataAccessException e) {
            // Handle other types of data access exceptions
            e.printStackTrace();
            result.put("message", "An error occurred while processing the request.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String deleteInstance(String tableName, String inputValue) {
        try {
            Map<String, String> primaryKeyDetails = getPrimaryKeyColumnDetails(tableName);
            String primaryKeyColumn = primaryKeyDetails.get("columnName");
            String primaryKeyColumnType = primaryKeyDetails.get("columnType");
            Object primaryKeyValue = convertToDataType(inputValue, primaryKeyColumnType);

            int rowsUpdated = jdbcTemplate.update("UPDATE " + tableName + " SET active = false WHERE " + primaryKeyColumn + " = "+ primaryKeyValue );
            if (rowsUpdated > 0) {
                return "Record updated successfully";
            } else {
                return "Record not found";
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "An error occurred while updating the record";
        }
    }
    private Map<String, String> getPrimaryKeyColumnDetails(String tableName) {
        Map<String, String> primaryKeyDetails = new HashMap<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet resultSet = metaData.getPrimaryKeys(null, null, tableName)) {
                if (resultSet.next()) {
                    String columnName = resultSet.getString("COLUMN_NAME");
                    primaryKeyDetails.put("columnName", columnName);

                    // Get the data type of the primary key column
                    try (ResultSet columns = metaData.getColumns(null, null, tableName, columnName)) {
                        if (columns.next()) {
                            String columnType = columns.getString("TYPE_NAME");
                            primaryKeyDetails.put("columnType", columnType);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return primaryKeyDetails;
    }

    private Object convertToDataType(String inputValue, String columnType) {
        Object convertedValue = null;
        try {
            switch (columnType.toLowerCase()) {
                case "int8":
                case "bigint":
                case "bigserial": // Handle bigserial columns as bigint
                case "serial":
                    convertedValue = Long.parseLong(inputValue);
                    break;
                case "int":
                case "integer":
                    convertedValue = Integer.parseInt(inputValue);
                    break;
                case "varchar":
                case "text":
                    convertedValue = inputValue;
                    break;
                // Add support for other data types as needed
                default:
                    throw new IllegalArgumentException("Unsupported column type: " + columnType);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input value for column type " + columnType + ": " + inputValue);
        }
        return convertedValue;
    }


}
