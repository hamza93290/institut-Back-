package com.institut.institut.builderDto;

import com.institut.institut.dto.JwtResponseDto;

public class JwtResponseDTOBuilder {
    private String accessToken;
    private String token;

    public JwtResponseDTOBuilder withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public JwtResponseDTOBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public JwtResponseDto build() {
        return new JwtResponseDto(accessToken, token);
    }
}
