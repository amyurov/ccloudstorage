package com.github.amyurov.cloudstorage.exception.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class RetrievedJWTException extends JWTVerificationException {

    public RetrievedJWTException(String msg) {
        super(msg);
    }

    public RetrievedJWTException(String message, Throwable cause) {
        super(message, cause);
    }
}
