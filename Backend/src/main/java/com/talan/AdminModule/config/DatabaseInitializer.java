package com.talan.AdminModule.config;

import com.talan.AdminModule.dto.ColumnInfo;
import com.talan.AdminModule.dto.TableInfo;
import com.talan.AdminModule.dto.TablesWithColumns;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DatabaseInitializer {



    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Getter
    private TablesWithColumns allTablesWithColumns =new TablesWithColumns();

    @PostConstruct
    private void initialize() {
         allTablesWithColumns = retrieveAllTablesWithColumns();

        for (TableInfo tableInfo : allTablesWithColumns.getAllTablesWithColumns()) {
            String active="active";
            if (tableInfo.getColumns().stream().noneMatch(columnInfo -> columnInfo.getName().equals(active))){
                jdbcTemplate.execute("ALTER TABLE " + tableInfo.getName() + " ADD COLUMN "+ active +" BOOLEAN DEFAULT TRUE");
            }
        }

         allTablesWithColumns =retrieveAllTablesWithColumns();
    }

    public TablesWithColumns retrieveAllTablesWithColumns() {
        List<TableInfo> tablesWithColumnsList = new ArrayList<>();
        TablesWithColumns tablesWithColumns = new TablesWithColumns();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, "public", null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                List<ColumnInfo> columns = new ArrayList<>();
                ResultSet tableColumns = metaData.getColumns(null, "public", tableName, null);
                while (tableColumns.next()) {
                    String columnName = tableColumns.getString("COLUMN_NAME");
                    String columnType = tableColumns.getString("TYPE_NAME");
                    columns.add(new ColumnInfo(columnName, columnType));
                }

                // Fetch primary key details
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                ColumnInfo primaryKeyColumn = new ColumnInfo();
                if (primaryKeys.next()) {
                    String primaryKeyColumnName = primaryKeys.getString("COLUMN_NAME");
                    String primaryKeyColumnType = columns.stream()
                            .filter(column -> column.getName().equals(primaryKeyColumnName))
                            .findFirst()
                            .map(ColumnInfo::getType)
                            .orElse("");
                    primaryKeyColumn.setName(primaryKeyColumnName);
                    primaryKeyColumn.setType(primaryKeyColumnType);
                }

                long totalRows = getTotalRowsCount(tableName, connection);

                TableInfo tableInfo = new TableInfo();
                tableInfo.setName(tableName);
                tableInfo.setColumns(columns);
                tableInfo.setPk(primaryKeyColumn);
                tableInfo.setTotalRows(totalRows);

                tablesWithColumnsList.add(tableInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        tablesWithColumns.setAllTablesWithColumns(tablesWithColumnsList);
        tablesWithColumns.setNumberTables((long)tablesWithColumnsList.size());
        return tablesWithColumns;
    }


    private int getTotalRowsCount(String tableName, Connection connection) throws SQLException {
        int totalRows = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
            if (resultSet.next()) {
                totalRows = resultSet.getInt(1);
            }
        }
        return totalRows;
    }

}
