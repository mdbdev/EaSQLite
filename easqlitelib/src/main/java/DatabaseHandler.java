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

public class DatabaseHandler extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 3;
    private static String DATABASE_NAME;
    private static String CREATE_TIMER_TABLE;
    public EaSQLite(Context context, String name){
        super(context, name, null, DATABASE_VERSION);
    	DATABASE_NAME = name;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tasks table if existed
        db.execSQL("DROP TABLE IF EXISTS tasks");

        // create fresh tasks table
        this.onCreate(db);
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

    public boolean addColumn(String name){
    	
    }
}
