package com.mdb.easqlitelib;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
