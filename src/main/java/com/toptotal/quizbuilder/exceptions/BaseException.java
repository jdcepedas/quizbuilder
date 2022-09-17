package com.toptotal.quizbuilder.exceptions;

public abstract class BaseException extends Exception {

    private String message = "";

    protected BaseException(String message, Throwable err) {
        super(message, err);
        this.message = message;
    }

    protected BaseException(String message) {
        this.message = message;
    }
}
