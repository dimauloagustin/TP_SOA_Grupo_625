package com.example.soa_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soa_v3.models.RegisterRequest;
import com.example.soa_v3.services.WebService;

public class RegisterActivity extends AppCompatActivity {
    private Button btnCancelar;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });

        IntentFilter filtro = new IntentFilter(ReceptorRegistro.ACTION_RESP);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(new ReceptorRegistro(), filtro);
    }
    public void cancelar() {
        finish();
    }

    public void enviar() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(((EditText)findViewById(R.id.editTextNombre)).getText().toString());
        registerRequest.setLastName (((EditText)findViewById(R.id.editTextApellido)).getText().toString());
        registerRequest.setDni (Integer.parseInt(((EditText)findViewById(R.id.editTextDNI)).getText().toString()));
        registerRequest.setEmail(((EditText)findViewById(R.id.editTextEmail)).getText().toString());
        registerRequest.setPassword (((EditText)findViewById(R.id.editTextPassword)).getText().toString());
        registerRequest.setGrupo(Integer.parseInt(((EditText)findViewById(R.id.editTextGrupo)).getText().toString()));
        registerRequest.setComision(Integer.parseInt(((EditText)findViewById(R.id.editTextComision)).getText().toString()));

        Intent i = new Intent(this, WebService.class);
        i.putExtra("url", "http://so-unlam.net.ar/api/api/register");
        try {
            i.putExtra("body", registerRequest.parse());
            Toast.makeText(this, "Vamos bien! ya tenes cuenta", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //error
        }
        startService(i);

    }


    public class ReceptorRegistro extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.example.soa_v3.intent.action.RESPUESTA_OPERACION_REGISTRO";

        @Override
        public void onReceive(Context contexto, Intent intento){
            boolean isSucces = intento.getBooleanExtra("isSucces",false);
            if(isSucces){
                finish();
            }else{
                //error
            }
        }
    }

}