package com.mdb.easqlitelib;

import android.content.Context;
import android.util.Pair;

import com.mdb.easqlitelib.exceptions.InvalidTypeException;

import java.util.Arrays;
import java.util.List;


public class EaSQLite {
    // Our static DatabaseHandler for managing database transactions
    private static DatabaseHandler dbHandler;

    /**
     * Initialize EaSQLite by initializing our Database Handler with a specified
     * context from the Application.
     *
     * @param context the context provided by the Application.
     */
    public static void initialize(Context context) {
        dbHandler = new DatabaseHandler(context);
    }

    // Create a table given a tableName
    public static boolean createTable(String tableName) {
        return dbHandler.createTable(tableName);
    }

    // Create a table given a tableName and columnList where Pair first is column name and second is type
    public boolean createTable(String tableName, Pair<String, String>[] columnList) {
        return dbHandler.createTable(tableName, columnList);
    }

    // Delete a table given the tableName
    public static boolean deleteTable(String tableName) {
        return dbHandler.deleteTable(tableName);
    }

    // Retrieve all Table Names
    public static String[] getTableNames() {
        return dbHandler.getTableNames();
    }

    // Give a String representation of a table given a tableName
    public static String tableToString(String tableName) {
        return null;
    }

    // Change the table name from currentName to newName
    public static boolean changeTableName(String currentName, String newName) {
        return dbHandler.changeTableName(currentName, newName);
    }

    // Get the number of columns for the table
    public static int getNumColumns(String tableName) {
        return dbHandler.getNumColumns(tableName);
    }

    // Get the number of rows for the table
    public static int getNumRows(String tableName) {
        return dbHandler.getNumRows(tableName);
    }

    //Add single column
    public static boolean addColumn(String tableName, String columnName, String type) throws InvalidTypeException {
        return dbHandler.addColumn(tableName, columnName, type);
    }

    //Add multiple columns with an array of names
    public static boolean addColumns(String tableName, Pair<String, String>[] columns) throws InvalidTypeException {
        return dbHandler.addColumns(tableName, columns);
    }

    // Retrieve a column as an array of objects
    public static Object[] getColumn(String tableName, String columnName) {
        return dbHandler.getColumn(tableName, columnName);
    }

    //Get the columns of the table corresponding to table name
    public static String[] getColumnNames(String tableName) {
        return dbHandler.getColumnNames(tableName);
    }

    //Add entry to SQLite database
    public static boolean addRow(String tableName, Pair<String, String>[] entries) throws InvalidTypeException {
        return dbHandler.addRow(tableName, entries);
    }

    //Delete entry from DB
    public static List<Object> deleteRow(String tableName, int id) {
        return dbHandler.deleteRow(tableName, id);
    }

    //Delete all entries from DB
    public static boolean deleteAllRows(String tableName) {
        return dbHandler.deleteAllRows(tableName);
    }

    //Get entry by entry id returned by create row
    public static List<Object> getRowById(String tableName, int id) {
        return dbHandler.getRowById(tableName, id);
    }

    //Create table to store custom objects
    public static String[] createTableFromObject(Object obj) {
        return null;
    }

    //Alternate method with name of object
    public static String[] createTableFromObject(String tableName, Object obj) {
        return null;
    }
}
