package com.example.soa_v3.models;

import org.json.JSONObject;

import java.util.HashMap;

public class EventRequest implements  IRequest{
    private String typeEvent;
    private String descripcion;

    public HashMap<String,String> headers;

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String event) {
        this.typeEvent = event;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void addToken(String token) { headers.put("token",token); }

    public String parse() throws Exception {

        headers = new HashMap<String, String>();

        JSONObject json = new JSONObject();

        json.put("env", "DEV");
        json.put("type_events", this.typeEvent);
        json.put("state", "ACTIVO");
        json.put("description", this.descripcion);

        return json.toString();
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
