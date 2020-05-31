package com.example.soa_v3.models;

import com.example.soa_v3.helpers.HttpRestHelper;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterRequest {
    private String name;
    private String lastName;
    private int dni;
    private String email;
    private String password;
    private int comision;
    private int grupo;

    public HashMap<String,String> headers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

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

    public int getComision() {
        return comision;
    }

    public void setComision(int comision) {
        this.comision = comision;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String parse() throws Exception {

        headers = new HashMap<String, String>();

        JSONObject json = new JSONObject();

        json.put("env", "TEST");
        json.put("name", this.name);
        json.put("lastname", this.lastName);
        json.put("dni", this.dni);
        json.put("email", this.email);
        json.put("password", this.password);
        json.put("commission", this.comision);
        json.put("group", this.grupo);

        return json.toString();
    }
}
