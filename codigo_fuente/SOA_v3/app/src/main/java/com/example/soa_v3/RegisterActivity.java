package com.example.soa_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    }
    public void cancelar() {
        finish();
    }

    public void enviar() {
        Toast.makeText(getApplicationContext(),"Se envió el formulario de registro",Toast.LENGTH_LONG).show();
        finish();
    }
}