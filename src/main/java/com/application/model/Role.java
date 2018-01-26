package com.application.model;

import lombok.Data;

@Data
public class Role {
    private String name;
    private String password;
    private String role;
    private boolean secret;
}
