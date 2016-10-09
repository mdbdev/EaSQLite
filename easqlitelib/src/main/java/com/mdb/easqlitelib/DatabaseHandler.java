package com.mdb.easqlitelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sirjan on 10/8/16.
 *
 * DatabaseHandler will manage all Database Transactions and deal
 * with executing String SQLite instructions.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static String DATABASE_NAME = "EaSQLiteDb";
    // List of the names of the tables DatabaseHandler contains
    private List<String> tableNames;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.tableNames = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String tableName : this.tableNames) {
            String execString = String.format(Strings.CREATE_TABLE, tableName) + Strings.SPACE
                    + Strings.ID_CONDITION;
            db.execSQL(execString);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String tableName : this.tableNames) {
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

    //Add single column
    public boolean addColumn(String tableName, String columnName, String type){
        return executeWrite(Strings.ALTER_TABLE +  tableName + Strings.ADD + columnName + Strings.SPACE + type);
    }

    //Add multiple columns with an array of names
    public boolean addColumns(String tableName, Pair<String, String>[] columns){
        boolean success = true;
        for(Pair p : columns){
            success &= executeWrite(Strings.ALTER_TABLE +  tableName + Strings.ADD + p.first + Strings.SPACE + p.second);
        }
        return success;
    }

    //Delete single column
    public boolean deleteColumn(String tableName, String columnName){
        return executeWrite(Strings.ALTER_TABLE +  tableName + Strings.DROP + columnName);
    }

    //Delete multiple columns with an array of names
    public boolean deleteColumns(String tableName, String[] columnNames){
        boolean success = true;
        for(String columnName : columnNames){
            success &= executeWrite(Strings.ALTER_TABLE +  tableName + Strings.DROP + columnName);
        }
        return success;
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
        return true;
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
    public Object[] getRowById(String tableName, int id){
        return null;
    }
    //Create table to store custom objects
    public String[] createTableFromObject(Object obj){
        return null;
    }
    //Alternate method with name of object
    public String[] createTableFromObject(String tableName, Object obj) {
        return null;
    }
}
