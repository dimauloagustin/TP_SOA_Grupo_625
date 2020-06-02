package com.example.soa_v3.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.soa_v3.RegisterActivity;
import com.example.soa_v3.helpers.ConectionHelper;
import com.example.soa_v3.helpers.HttpRestHelper;
import com.example.soa_v3.helpers.MagicHelper;
import com.example.soa_v3.models.IRequest;

public class WebService extends IntentService {

    public WebService() {
        super("WebService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getExtras().getString("url");
        String magicKey = intent.getExtras().getString("magic");
        IRequest req = MagicHelper.UseMagic(magicKey);
        Intent i = new Intent();
        String action = intent.getExtras().getString("action");
        i.setAction(action);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        try {

            if (ConectionHelper.isInternetAvailable()) {
                i.putExtra("res", new HttpRestHelper(url).send(req.parse(), req.getHeaders()));
                i.putExtra("isSucces", true);
            }
            else{
                throw new Exception("No internet conection");
            }
        } catch (Exception e) {
            i.putExtra("res",e.getMessage());
            i.putExtra("isSucces", false);
        }
        sendBroadcast(i);
    }
}
