package com.mdb.easqlitelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import com.mdb.easqlitelib.commands.TableCommand;
import com.mdb.easqlitelib.exceptions.InvalidTypeException;
import com.mdb.easqlitelib.structures.Table;

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
    // Map of the names of the tables DatabaseHandler contains
    private Map<String, Table> tableMap;
    // Map of the names to a respective TableCommand
    private Map<String, TableCommand> tableCommandsMap;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.tableMap = new HashMap<>();
        this.tableCommandsMap = new HashMap<>();
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
    public boolean createTable(String tableName) {
        Table table = new Table(tableName);
        String createTableCommand = String.format(Strings.CREATE_TABLE, tableName) + Strings.SPACE
                    + Strings.ID_CONDITION;
        if (tableMap.containsKey(tableName)) {
            return false;
        } else {
            tableMap.put(tableName, table);
            return executeWrite(createTableCommand);
        }
    }

    //Add single column
    public boolean addColumn(String tableName, String columnName, String type) throws InvalidTypeException{
        return colAdder(tableMap.get(tableName), columnName, type);
    }

    //Add multiple columns with an array of names
    public boolean addColumns(String tableName, Pair<String, String>[] columns) throws InvalidTypeException{
        Table table = tableMap.get(tableName);
        boolean success = true;
        for(Pair<String, String> p : columns){
            success &= colAdder(table, p.first, p.second);
        }
        return success;
    }

    //Get the column names of the table
    public String[] getColumnNames(String tableName) {
        Table table = tableMap.get(tableName);
        return table.getColumnNames();
    }

    //Add entry to SQLite database
    public boolean addRow(String tableName, Pair<String, String>[] entries) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (Pair<String, String> p : entries) {
            cv.put(p.first, p.second);
        }
        return db.insert(tableName, null, cv) > 0;
    }

    //Delete entry from DB
    public boolean deleteRow(String tableName, int id){
        Table table = tableMap.get(tableName);
        TableCommand tableCommand = tableCommandsMap.get(tableName);
        return false;
    }

    //Delete first entry from DB
    public boolean deleteFirstRow(String tableName){
        return true;
    }

    //Delete last entry from DB
    public boolean deleteLastRow(String tableName){
        return true;
    }

    //Delete all entries from DB
    public boolean deleteAllRows(String tableName){
        return true;
    }

    //Get entry by entry id returned by create row
    public List<Object> getRowById(String tableName, int id){
        return tableMap.get(tableName).getEntries().get(id).data;
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
    private boolean colAdder(Table table, String columnName, String type) throws InvalidTypeException{
        String command = table.addColumn(columnName, type);
        return executeWrite(command);
    }
}
