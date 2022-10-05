package com.quizbuilder.exceptions;

public class UserException extends BaseException {

    public UserException(String message, Throwable err) {
        super(message, err);
    }

    public UserException(String message) {
        super(message);
    }
}
