package com.toptotal.quizbuilder.payload.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;
}
