package com.talan.adminmodule.config;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.talan.adminmodule.dto.ColumnInfo;
import com.talan.adminmodule.dto.RegisterDto;
import com.talan.adminmodule.dto.TableInfo;
import com.talan.adminmodule.dto.TablesWithColumns;
import com.talan.adminmodule.entity.Role;
import com.talan.adminmodule.entity.User;
import com.talan.adminmodule.repository.UserRepository;
import com.talan.adminmodule.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer {


    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log =  LoggerFactory.getLogger(DatabaseInitializer.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializer( DataSource dataSource,JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate=jdbcTemplate;
    }
    @Getter
    private TablesWithColumns allTablesWithColumns =new TablesWithColumns();
@Autowired
UserRepository userService;
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
      /*  User user = User.builder()
                .firstname("fedi")
                .lastname("jat")
                .email("jatlaouimedfedi@gmail.com")
                .company("")
                .phone("")
                .password(passwordEncoder.encode("123"))
                .role(Role.ADMIN)
                .profileImagePath("profileImagePath")
                .active(true)
                .nonExpired(true)
                .build();
        userService.save(user);*/

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

                long totalRows = getTotalRowsCount(tableName);

                TableInfo tableInfo = new TableInfo();
                tableInfo.setName(tableName);
                tableInfo.setColumns(columns);
                tableInfo.setPk(primaryKeyColumn);
                tableInfo.setTotalRows(totalRows);

                tablesWithColumnsList.add(tableInfo);
            }
        } catch (SQLException e) {
            log.error("An error occurred: {}", e.getMessage());
        }

        tablesWithColumns.setAllTablesWithColumns(tablesWithColumnsList);
        tablesWithColumns.setNumberTables((long)tablesWithColumnsList.size());
        return tablesWithColumns;
    }


    private int getTotalRowsCount(String tableName)  {
        int totalRows = 0;

        String sql = String.format("SELECT COUNT(*) FROM %s", tableName);
        Integer result = jdbcTemplate.queryForObject(sql,Integer.class);
        if (result!=null){
            totalRows=result;
        }
        return totalRows;
    }

}
