package com.example.soa_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soa_v3.helpers.MagicHelper;
import com.example.soa_v3.models.LoginRequest;
import com.example.soa_v3.models.LoginResponse;
import com.example.soa_v3.models.RegisterRequest;
import com.example.soa_v3.services.WebService;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button btnRegistrarse;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MagicHelper.InitMagicHelper();

        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarse();
            }
        });

        IntentFilter filtro = new IntentFilter(ReceptorLog.ACTION_RESP);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(new ReceptorLog(), filtro);
    }

    public void login() {¿
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(((EditText)findViewById(R.id.editEmail)).getText().toString());
        loginRequest.setPassword (((EditText)findViewById(R.id.editPassword)).getText().toString());

        Intent i = new Intent(this, WebService.class);
        i.putExtra("url", "http://so-unlam.net.ar/api/api/login");
        i.putExtra("action", MainActivity.ReceptorLog.ACTION_RESP);
        try {
            String magicKey = UUID.randomUUID().toString();
            i.putExtra("magic", magicKey);
            MagicHelper.AddMagic(magicKey, loginRequest);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        startService(i);
    }

    public void registrarse() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public class ReceptorLog extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.example.soa_v3.intent.action.RESPUESTA_OPERACION_LOG";

        @Override
        public void onReceive(Context contexto, Intent intento){
            boolean isSuccess = intento.getBooleanExtra("isSucces",false);
            if(isSuccess){
                try {
                    LoginResponse loginResponse = new LoginResponse(intento.getStringExtra("res"));
                    if(loginResponse.getState().equals( "success")) {
                        //Toast.makeText(contexto.getApplicationContext(), loginResponse.getToken(), Toast.LENGTH_LONG).show();
                        //finish();
                        MagicHelper.token = loginResponse.getToken();
                        Intent intent = new Intent(contexto.getApplicationContext(), DataActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(contexto.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(contexto.getApplicationContext(), intento.getStringExtra("res"), Toast.LENGTH_LONG).show();
            }
        }
    }
}
