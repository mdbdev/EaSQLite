import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EaSQLite extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 3;
    private static String DATABASE_NAME;
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

    public boolean addRow(String tableName, Object[] entries) {

    }
}
