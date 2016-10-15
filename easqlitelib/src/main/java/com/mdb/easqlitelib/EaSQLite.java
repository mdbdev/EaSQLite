package com.mdb.easqlitelib;
import android.content.Context;
import android.util.Pair;


public class EaSQLite {
    // Our static DatabaseHandler for managing database transactions
    private static DatabaseHandler dbHandler;

    /**
     * Initialize EaSQLite by initializing our Database Handler with a specified
     * context from the Application.
     * @param context the context provided by the Application.
     */
    public void initialize(Context context) {
        dbHandler = new DatabaseHandler(context);
    }

    //Add single column
    public static boolean addColumn(String tableName, String columnName, String type){
        return dbHandler.addColumn(tableName, columnName, type);
    }

    //Add multiple columns with an array of names
    public static boolean addColumns(String tableName, Pair<String, String>[] columns){
        return dbHandler.addColumns(tableName, columns);
    }

    //Add entry to SQLite database
    public static boolean addRow(String tableName, Pair<String, String>[] entries) {
        return dbHandler.addRow(tableName, entries);
    }

    //Delete entry from DB
    public static boolean deleteRow(String tableName, int id){
        return true;
    }

    //Delete first entry from DB
    public static boolean deleteFirstRow(String tableName){
        return true;
    }

    //Delete last entry from DB
    public static boolean deleteLastRow(String tableName){
        return true;
    }

    //Delete all entries from DB
    public static boolean deleteAllRows(String tableName){
        return true;
    }

    //Get entry by entry id returned by create row
    public static Object[] getRowById(String tableName, int id){
        return null;
    }

    //Create table to store custom objects
    public static String[] createTableFromObject(Object obj){
        return null;
    }

    //Alternate method with name of object
    public static String[] createTableFromObject(String tableName, Object obj) {
        return null;
    }
}
