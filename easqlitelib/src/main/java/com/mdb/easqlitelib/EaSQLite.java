package com.mdb.easqlitelib;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EaSQLite extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 3;
    //Database Name
    private static String DATABASE_NAME = "AppDB";
    //Initial create table statement
    private static String CREATE_TABLE;

    public EaSQLite(Context context, String tableName){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	CREATE_TABLE = "CREATE TABLE"+  tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT) ";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tasks table if existed
        db.execSQL("DROP TABLE IF EXISTS tasks");

        // create fresh tasks table
        this.onCreate(db);
    }
    //Add single column
    public static boolean addColumn(String tableName, String columnName, String type){

        SQLiteDatabase db = this.getWritableDatabase();

    	db.execSQL("ALTER TABLE " +  tableName + " ADD " + columnName + " " + type);

        return true;

    }
    //Add multiple columns with an array of names
    public static boolean addColumns(String tableName, Pair<String, String>[] columns){

        SQLiteDatabase db = this.getWritableDatabase();

        for(Pair p : columns){

        db.execSQL("ALTER TABLE " +  tableName + " ADD " + p.first + " " + p.second);
        }
        return true;

    }
    //Delete single column
    public static boolean deleteColumn(String tableName, String columnName, String type){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("ALTER TABLE " +  tableName + " DROP " + columnName + " " + type);

        return true;
    }
    //Delete multiple columns with an array of names
    public static boolean deleteColumns(String tableName, String[] columnNames){

        SQLiteDatabase db = this.getWritableDatabase();

        for(String columnName : columnNames){

        db.execSQL("ALTER TABLE " +  tableName + " DROP " + columnName);
        }
        return true;
    }
    //Add entry to SQLite database
    public static boolean addRow(String tableName, Pair<String, String>[] entries) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (Pair<String, String> p : entries) {
            cv.put(p.first, p.second);
        }
        db.insert(tableName, null, cv);
        return true;
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
