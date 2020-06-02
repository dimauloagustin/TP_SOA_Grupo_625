package com.example.soa_v3.models;

import org.json.JSONObject;

public class LoginResponse {

    private String state;
    private String token;

    public LoginResponse(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        this.state = jsonObject.getString("state");
        this.token = jsonObject.getString("token");
    }

    public String getState() {
        return state;
    }
    public String getToken() { return token; }
}
