package com.mdb.easqlitelib.structures;

import com.mdb.easqlitelib.exceptions.InvalidTypeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of a Row in a SQLite Database
 */

public class Entry {
    public long id;
    public List<Object> data;
    public Table table;

    public Entry(long id, List<Object> list, Table table) {
        this.id = id;
        this.table = table;
        data = new ArrayList<Object>(list);
    }

    private String classType(Object o) {
        if (o == null) {
            return "NULL";
        }
        Class c = o.getClass();
        if (c==Integer.class) {
            return "INTEGER";
        } else if (c==Float.class||c==Double.class) {
            return "REAL";
        } else if (c==char[].class||c==String.class) {
            return "TEXT";
        } else {
            return null;
        }
    }

    public void addField(Object field) throws InvalidTypeException{
        String type = classType(field);
        if (type != null && !type.equals(table.getSchema().get(data.size()))) {
            throw new InvalidTypeException("Field type does not match schema");
        } else {
            data.add(field);
        }
    }

    public boolean verifyFields(){
        for (int i = 0; i < data.size(); i++) {
            String type = classType(data.get(i));
            if (type == null || !type.equals(table.getSchema().get(i))) {
                return false;
            }
        }
        return true;
    }

}
