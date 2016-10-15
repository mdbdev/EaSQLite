package com.mdb.easqlitelib.structures;

/**
 * Abstraction of a column in SQLite Database
 */

public class Column {
    public String name;
    public String type;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
