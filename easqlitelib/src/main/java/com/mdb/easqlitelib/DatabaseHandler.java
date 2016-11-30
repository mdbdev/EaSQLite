package com.mdb.easqlitelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import com.mdb.easqlitelib.exceptions.InvalidInputException;
import com.mdb.easqlitelib.exceptions.InvalidTypeException;
import com.mdb.easqlitelib.structures.Entry;
import com.mdb.easqlitelib.structures.Table;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DatabaseHandler will manage all Database Transactions and deal
 * with executing String SQLite instructions.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static String DATABASE_NAME = "EaSQLiteDb";
    // Table Name Schema (bootstrapping)
    private static String TABLE_NAME_SCHEMA = "table_name_schema";
    // Map of the names of the tables DatabaseHandler contains
    private Map<String, Table> tableMap;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.tableMap = new HashMap<>();
        this.tableMap.put(TABLE_NAME_SCHEMA, new Table(TABLE_NAME_SCHEMA));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String tableName : this.tableMap.keySet()) {
            String execString = String.format(Strings.CREATE_TABLE, tableName) + Strings.SPACE
                    + Strings.ID_CONDITION;
            db.execSQL(execString);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String tableName : this.tableMap.keySet()) {
            String execString = String.format(Strings.DROP_TABLE, tableName);
            db.execSQL(execString);
        }
    }

    /**
     * Loads up tableMap with all the tables in the database.
     */
    public void initializeAllTables() throws InvalidTypeException, InvalidInputException, IOException, ClassNotFoundException {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> tableNames = getLocalStorageTableNames(db);

        for (String tableName : tableNames) {
            Table table = createTableFromLocalStorage(tableName, db);
            this.tableMap.put(tableName, table);
        }
    }

    /**
     * Executes a SQL command that involves writing to the Database.
     * @param command the SQL command to be executed.
     */
    public boolean executeWrite(String command) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(command);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    // Create a table from a tableName
    public boolean createTable(String tableName) throws InvalidInputException {
        Table table = new Table(tableName);
        if (tableName.contains(" ")) {
            throw new InvalidInputException("Table name cannot have spaces");
        }
        String createTableCommand = String.format(Strings.CREATE_TABLE, tableName) + Strings.SPACE
                    + Strings.ID_CONDITION;
        if (tableMap.containsKey(tableName)) {
            return false;
        } else {
            tableMap.put(tableName, table);
            return executeWrite(createTableCommand);
        }
    }
    // Create a table given a tableName and columnList where Pair first is column name and second is type

    public boolean createTable(String tableName, Pair<String, String>[] columnList) throws InvalidInputException {
        boolean working = createTable(tableName);
        for (Pair<String, String> p : columnList) {
            try {
                working &= addColumn(tableName, p.first, p.second);
            } catch (Exception e) {
                System.out.print(e);
            }
        }
        return working;
    }
    // Delete table form a tableName
    public boolean deleteTable(String tableName){
        String deleteTableCommand = String.format(Strings.DROP_TABLE, tableName);
        return executeWrite(deleteTableCommand);
    }
    //Add single column
    public boolean addColumn(String tableName, String columnName, String type) throws InvalidTypeException, InvalidInputException{
        Table table = tableMap.get(tableName);
        if (table == null) return false;
        return colAdder(table, columnName, type);
    }

    //Add multiple columns with an array of names
    public boolean addColumns(String tableName, Pair<String, String>[] columns) throws InvalidTypeException, InvalidInputException{
        Table table = tableMap.get(tableName);
        if (table == null) return false;
        boolean success = true;
        for(Pair<String, String> p : columns){
            success &= colAdder(table, p.first, p.second);
        }
        return success;
    }
    //Change the table name
    public boolean changeTableName(String tableName, String newName) throws InvalidInputException {
        if (newName.contains(" ")) {
            throw new InvalidInputException("New table name cannot contain space");
        }
        if (tableMap.get(tableName) == null) return false;
        String changeTableNameCommand = Strings.ALTER_TABLE + tableName + Strings.RENAME_TO + newName;
        return executeWrite(changeTableNameCommand);
    }
    //Get the column names of the table
    public List<String> getColumnNames(String tableName) {
        Table table = tableMap.get(tableName);
        if (table == null) return null;
        return table.getColumnNames();
    }

    //Add entry to SQLite database
    public int addRow(String tableName, Pair<String, Object>[] entries) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Table table = tableMap.get(tableName);
        if (table == null) return -1;
        List<Object> list = new ArrayList<>(entries.length);
        for (Pair<String, Object> p : entries) {
            cv.put(p.first, serialize(p.second));
            int i = table.getColumnIndex(p.first);
            if (i < 0) return -1;
            list.add(i, p.second);
        }
        long id = db.insert(tableName, null, cv);
        if (id < 0) return -1;
        Entry entry = new Entry(id, list, table);
        table.addEntry(entry);
        return (int)id;
    }

    //Delete entry from DB
    public List<Object> deleteRow(String tableName, int id){
        String command = Strings.DELETE_FROM + tableName + Strings.WHERE + "id=" + id;
        if (!executeWrite(command))
            return null;
        Table table = tableMap.get(tableName);
        if (table == null) return null;
        List<Object> entry = table.removeEntry(id);
        return entry;
    }

    //Delete all entries from DB
    public boolean deleteAllRows(String tableName){
        Table table = tableMap.get(tableName);
        if (table == null) return false;
        table.getEntries().clear();
        String command = Strings.DELETE_FROM + tableName;
        return executeWrite(command);
    }

    //Get entry by entry id returned by create row
    public List<Object> getRowById(String tableName, int id){
        Table table = tableMap.get(tableName);
        if (table == null) return null;
        return new ArrayList<>(table.getEntries().get(id).data);
    }

    //Get unordered array of the values under a column
    public List<Object> getColumn(String tableName, String colName) {
        Table table = tableMap.get(tableName);
        if (table == null) return null;
        Map<Long, Entry> entries = table.getEntries();
        List<Object> column = new ArrayList<>(entries.size());
        int index = table.getColumnIndex(colName);
        int pos = 0;
        for (Entry e : entries.values()) {
            column.add(pos, e.data.get(index));
            pos ++;
        }
        return column;
    }

    //Get number of columns in a table
    public int getNumColumns(String tableName) {
        Table table = tableMap.get(tableName);
        if (table == null) return -1;
        return table.getColumnNames().size();
    }

    //Get number of columns in a table
    public int getNumRows(String tableName) {
        Table table = tableMap.get(tableName);
        if (table == null) return -1;
        return table.getEntries().size();
    }

    //Create table to store custom objects
    public String[] createTableFromObject(Object obj){
        return null;
    }

    //Alternate method with name of object
    public String[] createTableFromObject(String tableName, Object obj) {
        return null;
    }

    //Helper function for adding columns
    private boolean colAdder(Table table, String columnName, String type) throws InvalidTypeException, InvalidInputException{
        String command = table.addColumn(columnName, type);
        return executeWrite(command);
    }

    /**
     * Retrives the names of all the tables in the database.
     * @param db the SQLiteDatabase used.
     * @return   a List of Strings of the table names.
     */
    private List<String> getLocalStorageTableNames(SQLiteDatabase db) {
        List<String> tableNames = new ArrayList<>();
        String query = String.format(Strings.SELECT_ALL, TABLE_NAME_SCHEMA);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(1);
                    tableNames.add(name);
                } while (cursor.moveToFirst());
            }
            cursor.close();
        }
        return tableNames;
    }

    /**
     * Creates a Table object from a table currently stored locally.
     * @param tableName the name of the table.
     * @param db        the SQLiteDatabase used.
     * @return          a table object representation of the locally stored table.
     */
    private Table createTableFromLocalStorage(String tableName, SQLiteDatabase db) throws InvalidTypeException, InvalidInputException, IOException, ClassNotFoundException {
        String query = String.format(Strings.SELECT_ALL, tableName);
        Cursor cursor = db.rawQuery(query, null);
        Table table = new Table(tableName);
        int columnIndex = 0;
        if (cursor != null) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : cursor.getColumnNames()) {
                try {
                    table.addColumn(columnName, getTypeFromInt(cursor.getType(columnIndex)));
                } catch (InvalidTypeException e) {
                    return new Table(tableName); // Should never reach here.
                }
                columnIndex++;
            }

            if (cursor.moveToFirst()) {
                do {
                    List<Object> entryList = new ArrayList<>();

                    for (int i = 1; i < columnNames.length; i++) {
                        entryList.add(getCursorObject(cursor, i));
                    }

                    Entry entry = new Entry(cursor.getLong(0), entryList, table);
                    table.addEntry(entry);
                } while (cursor.moveToFirst());
            }
            cursor.close();
        }
        return table;
    }

    /**
     * Gives a String type from an integer type.
     * @param type the integer type.
     * @return     the String type.
     */
    private String getTypeFromInt(int type) {
        switch (type) {
            case 0:
                return "NULL";
            case 1:
                return "INTEGER";
            case 2:
                return "REAL";
            case 3:
                return "TEXT";
            default:
                return "TEXT";
        }
    }

    private Object getCursorObject(Cursor cursor, int colIndex) throws IOException, ClassNotFoundException {
        int type = cursor.getType(colIndex);

        switch (type) {
            case 0:
                return null;
            case 1:
                return cursor.getInt(colIndex);
            case 2:
                return cursor.getDouble(colIndex);
            case 3:
                return cursor.getString(colIndex);
            case 4:
                return deserialize(cursor.getBlob(colIndex));
            default:
                return cursor.getString(colIndex);
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public List<String> getTableNames(){
        List<String> tableNames = new ArrayList<>(tableMap.size());
        for (String s : tableMap.keySet()) {
            tableNames.add(s);
        }
        return tableNames;
    }
}
