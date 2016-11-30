package com.mdb.easqlitelib.structures;

import com.mdb.easqlitelib.Strings;
import com.mdb.easqlitelib.exceptions.InvalidInputException;
import com.mdb.easqlitelib.exceptions.InvalidTypeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andy on 10/8/16
 *
 * An abstraction of a SQLite Table
 */

public class Table {
    // The name of the Table
    private String tableName;

    // The types of columns in order
    private List<String> schema;

    // The Entry Rows of the Table
    private Map<Long, Entry> entries;

    // A mapping from column names to the actual column
    private Map<String, Column> columns;

    // A list of column names in order
    private List<String> columnList;

    // A mapping from column name to index
    private Map<String, Integer> columnIndex;

    public Table(String tableName) {
        this.tableName = tableName;
        this.schema = new ArrayList<>();
        this.columns = new HashMap<>();
        this.entries = new HashMap<>();
        this.columnList = new ArrayList<>();
        this.columnIndex = new HashMap<>();
    }

    /**
     * This method takes in a specified column and adds it into the Table.
     * It updates the schema and all the entries already part of the Table.
     * @param columnName the name of the column to be added.
     * @param type the SQL type of the column to be added.
     */
    public String addColumn(String columnName, String type) throws InvalidTypeException, InvalidInputException {
        if (columnName.contains(" ")) {
            throw new InvalidInputException("Column name cannot contain space");
        } else if (type.contains(" ")) {
            throw new InvalidInputException("Type cannot contain space");
        }
        Column column = new Column(columnName, type);
        columns.put(columnName, column);
        schema.add(type);
        columnList.add(columnName);
        columnIndex.put(columnName, columnIndex.size());

        for (Entry entry : entries.values()) {
            entry.addField(null);
        }

        return Strings.ALTER_TABLE + tableName + Strings.ADD + columnName + Strings.SPACE + type;
    }

    /**
     * This method takes in an entry to add into the table. It first verifies
     * that the entry is valid in that its types match the schema.
     * @param entry the entry to be added to the table.
     */
    public void addEntry(Entry entry) {
        entries.put(entry.id, entry);
    }

    public Map<Long, Entry> getEntries(){
        return entries;
    }

    /**
     * This method takes in a column name and returns the index
     * of that column in the ordered list of columns
     * @param columnName the name of the column.
     */
    public int getColumnIndex(String columnName) {
        Integer i = columnIndex.get(columnName);
        return (i != null) ? i : -1;
    }

    /**
     * This method removes a specified entry from the table.
     * @param id the id of the entry to be removed from the table.
     */
    public List<Object> removeEntry(int id) {
        Entry entry = entries.remove(id);
        return entry.data;
    }

    public List<String> getSchema() {
        return schema;
    }

    /**
     * Retrieves all the column names of the table in order.
     * @return String array of all the column names.
     */
    public List<String> getColumnNames() {
        return columnList;
    }
}
