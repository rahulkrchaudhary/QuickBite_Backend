package com.rahul.request;

import lombok.Data;

@Data
public class PasswordResetRequest {

    private String password;
    private String token;
}
