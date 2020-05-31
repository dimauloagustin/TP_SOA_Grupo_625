package com.example.soa_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DataActivity extends AppCompatActivity implements SensorEventListener{
    private Button btnSalir;
    private TableLayout tblLayout;
    private SensorManager sensorManager;
    private TextView tvDataTemperatura;
    private TextView tvDataLuz;

    boolean temp = false;
    boolean light = false;
    String tempString = "";
    String lightString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        btnSalir = (Button) findViewById(R.id.btnSalir);
        tblLayout = (TableLayout) findViewById(R.id.tvDataTableLayout);
        tvDataLuz = (TextView) findViewById(R.id.tvDataLuz);
        tvDataTemperatura = (TextView) findViewById(R.id.tvDataTemperatura);




        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void addColumn(String dato1, String dato2){
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

    private void updateViewSensores(String luz, String temperatura) {
        tvDataLuz.setText(luz);
        tvDataTemperatura.setText(temperatura);
    }

    public void salir() {
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        synchronized (this){
            switch (event.sensor.getType()){
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    tempString = event.values[0] + "";
                    temp = true;
                    //sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));
                    break;
                case Sensor.TYPE_LIGHT:
                    lightString = event.values[0] + "";
                    light = true;
                    //sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                    break;
            }
            if(temp&&light){
                updateViewSensores(lightString, tempString);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
