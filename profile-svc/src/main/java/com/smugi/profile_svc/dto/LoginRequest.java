package com.smugi.profile_svc.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
