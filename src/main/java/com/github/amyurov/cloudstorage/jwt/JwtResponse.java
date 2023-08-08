package com.github.amyurov.cloudstorage.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class JwtResponse {
    @JsonProperty("auth-token")
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
