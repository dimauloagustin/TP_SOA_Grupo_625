package com.example.soa_v3.helpers;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRestHelper {
    private String url;

    public HttpRestHelper(String url){
        this.url = url;
    }

    public String send(String json, HashMap<String,String> headers) throws Exception {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(4000);
            connection.setRequestMethod("POST");

            for(Map.Entry<String,String> item : headers.entrySet()){
                connection.setRequestProperty(item.getKey(),item.getValue());
            }

            DataOutputStream wr =new DataOutputStream(connection.getOutputStream());
            wr.write(json.getBytes("UTF-8"));
            wr.flush();
            wr.close();

        connection.connect();
        int responseCode= connection.getResponseCode();
        String res = this.streamReader(new InputStreamReader(connection.getInputStream()));

        connection.disconnect();
        return res;

    }

    private String streamReader(InputStreamReader input) throws Exception {
        BufferedReader streamReader = new BufferedReader(input);
        StringBuilder respondStreamBuild = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            respondStreamBuild.append(inputStr);

        return respondStreamBuild.toString();
    }

}
