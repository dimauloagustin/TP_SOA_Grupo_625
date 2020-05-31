package com.example.soa_v3.models;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginRequest {
    private String email;
    private String password;

    public HashMap<String,String> headers;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String parse() throws Exception {

        headers = new HashMap<String, String>();

        JSONObject json = new JSONObject();

        json.put("env", "TEST");
        json.put("email", this.email);
        json.put("password", this.password);

        return json.toString();
    }
}
