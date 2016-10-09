package com.mdb.easqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final int DATABASE_VERSION = 1;
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
    public void executeWrite(String command) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(command);
    }
}
