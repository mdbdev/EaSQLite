package com.mdb.easqlitelib;

import android.content.Context;
import android.util.Pair;

import com.mdb.easqlitelib.exceptions.InvalidInputException;
import com.mdb.easqlitelib.exceptions.InvalidTypeException;

import java.io.IOException;
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
     * @throws InvalidTypeException
     * @throws InvalidInputException
     */
    public static void initialize(Context context) throws InvalidTypeException, InvalidInputException, IOException, ClassNotFoundException {
        dbHandler = new DatabaseHandler(context);
        dbHandler.initializeAllTables();
    }

    /**
     * Creates a table with the specified tableName having no columns
     * nor rows initially.
     * @param tableName name the new table will have.
     * @return          boolean flag indicating the success of table creation.
     * @throws InvalidInputException
     */
    public static boolean createTable(String tableName) throws InvalidInputException {
        return dbHandler.createTable(tableName);
    }

    /**
     * Creates a table given a table name and a list of Pairs
     * holding column names and column types. Types can be any of the following:
     * "INTEGER", "TEXT", "REAL", "BLOB".
     * @param tableName  the name of the table to create.
     * @param columnList the list of pairs where each Pair holds the name, type of the column.
     * @return           boolean flag indicating the success of table creation.
     * @throws InvalidInputException
     */
    public boolean createTable(String tableName, Pair<String, String>[] columnList) throws InvalidInputException {
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
     * @return a List of Strings representing the table names in the DB.
     */
    public static List<String> getTableNames() {
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
     * @throws InvalidInputException
     */
    public static boolean changeTableName(String currentName, String newName) throws InvalidInputException {
        return dbHandler.changeTableName(currentName, newName);
    }

    /**
     * Returns the number of columns in a table specified by the table name. -1 if table not found.
     * @param tableName the name of the table used to identify the table to quantify columns.
     * @return          the number of columns of the specified table.
     */
    public static int getNumColumns(String tableName) {
        return dbHandler.getNumColumns(tableName);
    }

    /**
     * Returns the number of rows in a table specified by the table name. -1 if table not found.
     * @param tableName the name of the table used to identify the table to quantify rows.
     * @return          the number of columns of the specified table.
     */
    public static int getNumRows(String tableName) {
        return dbHandler.getNumRows(tableName);
    }

    //Add single column

    /**
     * Adds a single column to the table specified by a table name. The column should have a
     * specified column name and type.
     * @param tableName  the table name used to identify the table to add the column to.
     * @param columnName the name of the column to be added.
     * @param type       the type of the column to be added. The type can be: "INTEGER", "TEXT",
     *                   or "REAL."
     * @return           a boolean flag indicating success of the addition.
     * @throws InvalidTypeException
     * @throws InvalidInputException
     */
    public static boolean addColumn(String tableName, String columnName, String type) throws InvalidTypeException, InvalidInputException {
        return dbHandler.addColumn(tableName, columnName, type);
    }

    /**
     * Adds multiple columns to the table specified by an array of Pairs with the first
     * entry being a column name, and second entry being the type.
     * @param tableName the table name used to identify the table to add columns to.
     * @param columns   an array of Pairs that hold column names and column types.
     * @return          a boolean flag indicating success of the additions.
     * @throws InvalidTypeException
     * @throws InvalidInputException
     */
    public static boolean addColumns(String tableName, Pair<String, String>[] columns) throws InvalidTypeException, InvalidInputException {
        return dbHandler.addColumns(tableName, columns);
    }

    /**
     * Retrieves a column with a specified column name from a table specified by a certain
     * table name. The column is represented by a list of Objects.
     * @param tableName  the table name used to identify the table to get the column from.
     * @param columnName the column name used to identify the column to return.
     * @return           a List of objects representing a column in the Table.
     */
    public static List<Object> getColumn(String tableName, String columnName) {
        return dbHandler.getColumn(tableName, columnName);
    }

    /**
     * Gets an in-order List of column names in order from a table
     * @param tableName the name of the table to retrieve from
     * @return          a List of strings of the column names in the table,
     *                  in order
     */
    public static List<String> getColumnNames(String tableName) {
        return dbHandler.getColumnNames(tableName);
    }

    /**
     * Adds a new entry to a table, with an array of column names to entry value
     * @param tableName the name of the table to be added to
     * @param entries   the array of Pairs, formatted such that first is the column
     *                  name and second is the entry value for that column
     * @return          id of entry as integer
     */
    public static int addRow(String tableName, Pair<String, Object>[] entries) throws IOException {
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

    /**
    //Create table to store custom objects
    public static String[] createTableFromObject(Object obj) {
        return null;
    }

    //Alternate method with name of object
    public static String[] createTableFromObject(String tableName, Object obj) {
        return null;
    } */
}
