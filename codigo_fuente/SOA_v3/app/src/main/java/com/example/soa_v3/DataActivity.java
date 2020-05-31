package com.example.soa_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soa_v3.helpers.MagicHelper;
import com.example.soa_v3.models.EventRequest;
import com.example.soa_v3.models.EventResponse;
import com.example.soa_v3.models.LoginResponse;
import com.example.soa_v3.services.WebService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataActivity extends AppCompatActivity implements SensorEventListener{
    private Button btnSalir;
    private Button btnMedir;
    private TableLayout tblLayout;
    private SensorManager sensorManager;
    private TextView tvDataTemperatura;
    private TextView tvDataLuz;

    private static final  int MAX_VALUE_TABLE_VIEW = 10;
    float MAX_VALUE_LIGHT;
    int iLight = 0;
    int iTemperature = 0;

    private List<String> lsTemperature = new ArrayList<String>();
    private List<String> lsLight = new ArrayList<String>();

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SP_LIGHT = "light";
    private static final String SP_TEMPERATURE = "temperature";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnMedir = (Button) findViewById(R.id.btnMedir);
        tblLayout = (TableLayout) findViewById(R.id.tvDataTableLayout);
        tvDataLuz = (TextView) findViewById(R.id.tvDataLuz);
        tvDataTemperatura = (TextView) findViewById(R.id.tvDataTemperatura);

        btnMedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medir();
            }
        });


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        MAX_VALUE_LIGHT = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT).getMaximumRange();


        IntentFilter filtro = new IntentFilter(DataActivity.ReceptorEvent.ACTION_RESP);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(new DataActivity.ReceptorEvent(), filtro);

        loadData();
        loadViewTable();
    }

    private  void updateListWithNewValue(){

        lsTemperature.add(0, iTemperature + "");
        if(lsTemperature.size() > MAX_VALUE_TABLE_VIEW)
            lsTemperature.remove(lsTemperature.size() - 1);


        lsLight.add(0, iLight + "");
        if(lsLight.size() > MAX_VALUE_TABLE_VIEW)
            lsLight.remove(lsLight.size() - 1);
    }

    private void medir(){

        clearViewTable();
        updateListWithNewValue();



        EventRequest eventRequest = new EventRequest();
        eventRequest.setTypeEvent("datos guardados");
        eventRequest.setDescripcion("Temperatura: " + iTemperature + ", Luz: " + iLight);
        eventRequest.addToken(MagicHelper.token);
        Intent i = new Intent(this, WebService.class);
        i.putExtra("url", "http://so-unlam.net.ar/api/api/event");
        i.putExtra("action", DataActivity.ReceptorEvent.ACTION_RESP);
        try {
            String magicKey = UUID.randomUUID().toString();
            i.putExtra("magic", magicKey);
            MagicHelper.AddMagic(magicKey, eventRequest);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        startService(i);


        saveData();
        loadViewTable();
    }

    private void clearViewTable() {
        tblLayout.removeAllViews();
    }

    private void loadViewTable(){
        int maxSize = 0;

        if (lsLight.size() > lsTemperature.size()) {
            maxSize = lsLight.size();
        } else {
            maxSize = lsTemperature.size();
        }

        for(int i=0; i < maxSize; i++){
            String tmpTemperature = "-";
            String tmpLight = "-";
            if (i < lsTemperature.size()) {
                try {
                    tmpTemperature = converToHumanTemperature(Integer.parseInt(lsTemperature.get(i)));
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "pone un número!! temperatura" + e.getMessage(), Toast.LENGTH_SHORT).show();
                };

            }
            if (i < lsLight.size()) {
                try {
                    tmpLight = converToHumanLight(Integer.parseInt(lsLight.get(i)));
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "pone un número luz !!", Toast.LENGTH_SHORT).show();
                };
            }

            addRow(tmpLight,  tmpTemperature);
        }
    }

    private void addRow(String dato1, String dato2){
        TableRow tblRow = new TableRow(this);
        tblRow.setLayoutParams( new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
        TextView field1 = new TextView(this);
        field1.setLayoutParams( new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
        TextView field2 = new TextView(this);
        field2.setLayoutParams( new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
        field1.setText(dato1);
        field2.setText(dato2);

        tblRow.addView(field1);
        tblRow.addView(field2);

        tblLayout.addView(tblRow);
    }


    private String converToHumanTemperature(int iTemperature){
        return iTemperature + " C°";
    }

    private String converToHumanLight(int iLight){
        return ((iLight / 40000) * 100 )+ " %";
    }


    public void salir() {
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        synchronized (this){
            switch (event.sensor.getType()){
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    iTemperature = (int) event.values[0];
                    tvDataTemperatura.setText(converToHumanTemperature(iTemperature));
                    break;
                case Sensor.TYPE_LIGHT:
                    iLight = (int) event.values[0];
                    tvDataLuz.setText(converToHumanLight(iLight));
                    break;
            }
        }
    }


    private String converLSToString(List<String> list) {
        StringBuilder csvList = new StringBuilder();
        for(String s : list){
            csvList.append(s);
            csvList.append(",");
        }
        return csvList.toString();
    }

    private List<String> converStringToLS(String csvList) {
        String[] items = csvList.split(",");
        List<String> list = new ArrayList<String>();
        for(int i=0; i < items.length; i++){
            list.add(items[i]);
        }
        return list;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SP_LIGHT, converLSToString(lsLight));
        editor.putString(SP_TEMPERATURE, converLSToString(lsTemperature));

        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String dataLight = sharedPreferences.getString(SP_LIGHT, "");
        String dataTemperature = sharedPreferences.getString(SP_TEMPERATURE, "");

        if (dataLight != "") {
            lsLight = converStringToLS(dataLight);
        }
        if (dataTemperature != ""){
            lsTemperature = converStringToLS(dataTemperature);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class ReceptorEvent extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.example.soa_v3.intent.action.RESPUESTA_OPERACION_EVENT";

        @Override
        public void onReceive(Context contexto, Intent intento){
            boolean isSuccess = intento.getBooleanExtra("isSucces",false);
            if(isSuccess){
                try {
                    EventResponse eventResponse = new EventResponse(intento.getStringExtra("res"));
                    if(eventResponse.getState().equals( "success")) {
                        Toast.makeText(contexto.getApplicationContext(), "guardado en servidor", Toast.LENGTH_LONG).show();
                        //finish();
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
