package com.talan.adminmodule.service;

import com.talan.adminmodule.dto.ColumnInfo;
import com.talan.adminmodule.dto.TableInfo;
import com.talan.adminmodule.dto.TablesWithColumns;
import com.talan.adminmodule.config.DatabaseInitializer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)

class ParamTableServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private TablesWithColumns allTablesWithColumns; // New mock field

    @InjectMocks
    private ParamTableService paramTableService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


    }

    @Test
    void testRetrieveAllTablesWithFilteredColumns() {
        ColumnInfo pk = new ColumnInfo("PK1", "int");
        ColumnInfo pk1 = new ColumnInfo("PK2", "varchar");
        ColumnInfo pk2 = new ColumnInfo("PK3", "varchar");

        List<TableInfo> allTables = new ArrayList<>();
        allTables.add(new TableInfo("Table1", "Type1", pk, 100L, new ArrayList<>()));
        allTables.add(new TableInfo("Table2", "Type2", pk1, 200L, new ArrayList<>()));
        allTables.add(new TableInfo("Table3", "Type3", pk2, 300L, new ArrayList<>()));

        when(allTablesWithColumns.getAllTablesWithColumns()).thenReturn(allTables);
        when(allTablesWithColumns.getNumberTables()).thenReturn(3L);

        when(databaseInitializer.getAllTablesWithColumns()).thenReturn(allTablesWithColumns);

        int limit = 5;
        int offset = 0;

        TablesWithColumns result = paramTableService.retrieveAllTablesWithFilteredColumns(limit, offset);

        assertEquals(allTables.size(), result.getNumberTables());
        assertEquals(allTables.size(), result.getAllTablesWithColumns().size());
        assertEquals(allTables.get(0).getName(), result.getAllTablesWithColumns().get(0).getName());
        assertEquals(allTables.get(0).getType(), result.getAllTablesWithColumns().get(0).getType());
        assertEquals(allTables.get(0).getPk(), result.getAllTablesWithColumns().get(0).getPk());
        assertEquals(allTables.get(0).getTotalRows(), result.getAllTablesWithColumns().get(0).getTotalRows());

    }
}
