package com.toptotal.quizbuilder.exceptions;

import com.toptotal.quizbuilder.model.User;

public class UserException extends BaseException {

    public UserException(String message, Throwable err) {
        super(message, err);
    }

    public UserException(String message) {
        super(message);
    }
}
