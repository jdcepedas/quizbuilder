package com.quizbuilder.exceptions;

public class RoleException extends BaseException {

    public RoleException(String message, Throwable err) {
        super(message, err);
    }

    public RoleException(String message) {
        super(message);
    }
}
