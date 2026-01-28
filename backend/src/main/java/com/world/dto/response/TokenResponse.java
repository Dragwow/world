package com.world.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {

    private String token;

    public TokenResponse(String token1){
        token = token1;
    }
}
