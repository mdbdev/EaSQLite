package com.mdb.easqlitelib;

/**
 * Created by sirjan on 10/8/16.
 *
 * Strings contains useful strings for SQL.
 */

public class Strings {
    // General Strings
    public static final String SPACE = " ";

    // Table Creation Strings
    public static final String CREATE_TABLE = "CREATE TABLE %s";
    public static final String ID_CONDITION = "(id INTEGER PRIMARY KEY AUTOINCREMENT)";

    // Table Deletion Strings
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS %s";

    // Table Alteration Strings
    public static final String ALTER_TABLE = "ALTER TABLE ";
    public static final String ADD = " ADD ";
    public static final String DROP = " DROP ";

}
