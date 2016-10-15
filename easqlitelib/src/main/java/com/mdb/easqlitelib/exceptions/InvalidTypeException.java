package com.mdb.easqlitelib.exceptions;

/**
 * Created by andyw1997 on 10/9/16.
 * Invalid Type Exception thrown when adding
 * row values in that are not valid
 */

public class InvalidTypeException extends Exception {
    public InvalidTypeException(String message) {
        super(message);
    }
}
