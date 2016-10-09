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
    //Initial create table statment
    private static String CREATE_TABLE;
    // 
    public EaSQLite(Context context, String tableName){
    	CREATE_TABLE = "CREATE TABLE"+  tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT) "
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
    public boolean addColumn(String tableName, String columnName, String type){

    	db.execSQL("ALTER TABLE " +  tableName + " ADD " + columnName + " " + type);

    }
    //Add multiple columns with an array of names
    public boolean addColumns(String tableName, Pair<String, String>[] columns){

        for(Pair p : columns){

        db.execSQL("ALTER TABLE " +  tableName + " ADD " + columns.getLeft() + " " + columns.getRight());
        }

    }
    //Delete single column
    public boolean deleteColumn(String tableName, String columnName){


        db.execSQL("ALTER TABLE " +  tableName + " DROP " + columnName + " " + type);

    }
    //Delete multiple columns with an array of names
    public boolean deleteColumns(String tableName, String[] columnNames){

        for(String columnName : columns){

        db.execSQL("ALTER TABLE " +  tableName + " DROP " + columnName);
        }
    }
    public boolean addRow(String tableName, Pair<String, String>[] entries) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getReadableDatabase();
        for (Pair<String, String> p : entries) {
            cv.put(p.first, p.second);
        }
        db.insert(tableName, null, cv);
        return true;
    }
}
