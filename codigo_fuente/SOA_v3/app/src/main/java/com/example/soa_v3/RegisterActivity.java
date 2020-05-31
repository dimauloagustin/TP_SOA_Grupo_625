package com.example.soa_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soa_v3.helpers.ConectionHelper;
import com.example.soa_v3.helpers.MagicHelper;
import com.example.soa_v3.models.RegisterRequest;
import com.example.soa_v3.models.RegisterResponse;
import com.example.soa_v3.services.WebService;

import java.util.UUID;

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

        boolean isValid = true;
        if(!registerRequest.getEmail().contains("@")||!registerRequest.getEmail().contains(".")) {
            ((EditText)findViewById(R.id.editTextEmail)).setError("ingrese un email valido");
            isValid = false;
        }
        if(registerRequest.getPassword().length()<8) {
            ((EditText)findViewById(R.id.editTextPassword)).setError("contraseña minima de 8 caracteres");
            isValid = false;
        }

        if(isValid) {
            Intent i = new Intent(this, WebService.class);
            i.putExtra("url", "http://so-unlam.net.ar/api/api/register");
            i.putExtra("action", ReceptorRegistro.ACTION_RESP);
            try {
                //i.putExtra("body", registerRequest.parse());
                String magicKey = UUID.randomUUID().toString();
                i.putExtra("magic", magicKey);
                MagicHelper.AddMagic(magicKey, registerRequest);

                startService(i);

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public class ReceptorRegistro extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.example.soa_v3.intent.action.RESPUESTA_OPERACION_REGISTRO";

        @Override
        public void onReceive(Context contexto, Intent intento){
            boolean isSuccess = intento.getBooleanExtra("isSucces",false);
            if(isSuccess){
                try {
                    if(new RegisterResponse(intento.getStringExtra("res")).getState().equals( "success")) {
                        Toast.makeText(contexto.getApplicationContext(), "Vamos bien! ya tenes cuenta", Toast.LENGTH_LONG).show();
                        finish();
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