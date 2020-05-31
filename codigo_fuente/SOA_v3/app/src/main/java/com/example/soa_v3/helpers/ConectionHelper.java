package com.example.soa_v3.helpers;

import android.net.ConnectivityManager;

import java.net.InetAddress;

public class ConectionHelper {
    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
