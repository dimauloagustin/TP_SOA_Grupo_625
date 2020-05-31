package com.example.soa_v3.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.soa_v3.RegisterActivity;
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
       // String body = intent.getExtras().getString("body");
        String magicKey = intent.getExtras().getString("magic");
        IRequest req = MagicHelper.UseMagic(magicKey);
        Intent i = new Intent();
        i.setAction(RegisterActivity.ReceptorRegistro.ACTION_RESP);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            i.putExtra("res",new HttpRestHelper(url).send(req.parse(),req.getHeaders()));
            i.putExtra("isSucces", true);
        } catch (Exception e) {
            i.putExtra("res",e.getMessage());
            i.putExtra("isSucces", false);
        }
        sendBroadcast(i);
    }
}
