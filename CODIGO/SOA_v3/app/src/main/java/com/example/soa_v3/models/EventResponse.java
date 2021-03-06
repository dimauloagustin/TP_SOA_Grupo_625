package com.example.soa_v3.models;

import org.json.JSONObject;

public class EventResponse {

    private String state;

    public EventResponse(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        this.state = jsonObject.getString("state");
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
