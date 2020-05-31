package com.example.soa_v3.helpers;

import com.example.soa_v3.models.IRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MagicHelper {

    private static HashMap<String, IRequest> dust;

    public static void InitMagicHelper(){
        dust = new HashMap<String, IRequest>();
    }

    public static void AddMagic(String key,IRequest particle){
        synchronized (dust){
            dust.put(key, particle);
        }
    }

    public static IRequest UseMagic(String key){
        synchronized (dust){
            return dust.remove(key);
        }
    }

}
