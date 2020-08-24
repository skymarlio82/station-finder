package com.whiz.app.boot.domain;

import java.util.function.Supplier;

public class DataEntityNotFoundException extends Exception implements Supplier<String> {

    public DataEntityNotFoundException() {
        super("Entity data not found.");
    }

    public DataEntityNotFoundException(String msg) {
        super(msg);
    }

    public DataEntityNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String get() {
        return "Entity data not found.";
    }
}