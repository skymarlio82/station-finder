package com.whiz.app.boot.application;

import java.util.function.Supplier;

public class CommonBizException extends Exception implements Supplier<String> {

    public CommonBizException() {
        super("Common Exception from Biz Layer.");
    }

    public CommonBizException(String msg) {
        super(msg);
    }

    public CommonBizException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String get() {
        return "Common Exception from Biz Layer.";
    }
}