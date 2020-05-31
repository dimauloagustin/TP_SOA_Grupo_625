package com.example.soa_v3.models;

import java.util.HashMap;

public interface IRequest {
    public String parse() throws Exception;
    public HashMap<String,String> getHeaders();
}
