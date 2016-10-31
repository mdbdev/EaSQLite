package com.mdb.easqlitelib;

import android.content.Context;
import android.util.Pair;

import com.mdb.easqlitelib.exceptions.InvalidTypeException;

import java.util.List;


public class EaSQLite {
    // Static DatabaseHandler for managing database transactions.
    private static DatabaseHandler dbHandler;

    /**
     * Initializes EaSQLite by setting up a database handler with a
     * context from the Application. It will initialize all tables already
     * in local storage.
     *
     * @param context the context provided by the Application.
     */
    public static void initialize(Context context) throws InvalidTypeException {
        dbHandler = new DatabaseHandler(context);
        dbHandler.initializeAllTables();
    }

    /**
     * Creates a table with the specified tableName having no columns
     * nor rows initially.
     * @param tableName name the new table will have.
     * @return          boolean flag indicating the success of table creation.
     */
    public static boolean createTable(String tableName) {
        return dbHandler.createTable(tableName);
    }

    /**
     * Creates a table given a table name and a list of Pairs
     * holding column names and column types. Types can be any of the following:
     * "INTEGER", "TEXT", "REAL".
     * @param tableName  the name of the table to create.
     * @param columnList the list of pairs where each Pair holds the name, type of the column.
     * @return           boolean flag indicating the success of table creation.
     */
    public boolean createTable(String tableName, Pair<String, String>[] columnList) {
        return dbHandler.createTable(tableName, columnList);
    }

    /**
     * Deletes a table given a table name, if the table currently exists
     * in the database.
     * @param tableName the name of the table to be deleted.
     * @return          a boolean flag indicating the success of table deletion. Will return false
     *                  if the table does not currently exist in the DB.
     */
    public static boolean deleteTable(String tableName) {
        return dbHandler.deleteTable(tableName);
    }

    /**
     * Retrieves all the table names currently stored in the DB.
     * @return an array of Strings representing the table names in the DB.
     */
    public static String[] getTableNames() {
        return dbHandler.getTableNames();
    }

    // Give a String representation of a table given a tableName

    /**
     * Gives a String representation of a table specified by a passed in table name. Format will
     * be list of entries separated by commas with a newline at the end.
     * @param tableName name of the table to give String representation.
     * @return          String representation of the table.
     */
    public static String tableToString(String tableName) {
        return null;
    }

    /**
     * Changes the table name of a certain table from currentName to newName.
     * @param currentName the current name of the table used to find the table.
     * @param newName     the name that the table will change to.
     * @return            a boolean flag indicating the success of the change. Will return false
     *                    if a table with currentName cannot be found.
     */
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

    /**
     * Gets an in-order Array of column names in order from a table
     * @param tableName the name of the table to retrieve from
     * @return          an Array of strings of the column names in the table,
     *                  in order
     */
    public static String[] getColumnNames(String tableName) {
        return dbHandler.getColumnNames(tableName);
    }

    /**
     * Adds a new entry to a table, with an array of column names to entry value
     * @param tableName the name of the table to be added to
     * @param entries   the array of Pairs, formatted such that first is the column
     *                  name and second is the entry value for that column
     * @return          boolean indicating success of the addRow
     */
    public static boolean addRow(String tableName, Pair<String, String>[] entries) {
        return dbHandler.addRow(tableName, entries);
    }

    /**
     * Deletes an entry from a table with id, returns the values of entry
     * @param tableName name of the table from which the entry is removed
     * @param id        id of the entry, given by the addRow method
     * @return          List with values of entry in order
     */
    public static List<Object> deleteRow(String tableName, int id) {
        return dbHandler.deleteRow(tableName, id);
    }

    /**
     * Deletes all entries in a table
     * @param tableName name of table to delete all entries from
     * @return          success of deletion
     */
    public static boolean deleteAllRows(String tableName) {
        return dbHandler.deleteAllRows(tableName);
    }

    /**
     * Retrieves the values of an entry from a table with values in order
     * @param tableName the name of the table to retrieve entry from
     * @param id        the id of the entry to retrieve, given by the addRow
     *                  method
     * @return          a List of objects which are the values of the entry
     *                  in order
     */
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
