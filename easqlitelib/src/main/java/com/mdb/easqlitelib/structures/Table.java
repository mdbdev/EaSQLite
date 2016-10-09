package com.mdb.easqlitelib.structures;

import java.util.HashMap;

/**
 * An abstraction of a SQLite Table
 */

public class Table {
    private HashMap<String, Column> columns;
    private HashMap<Integer, Entry> entries;
}
