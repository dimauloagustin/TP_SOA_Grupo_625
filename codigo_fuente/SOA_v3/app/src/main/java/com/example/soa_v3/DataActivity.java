package com.example.soa_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {
    private Button btnSalir;
    private TableLayout tblLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        btnSalir = (Button) findViewById(R.id.btnSalir);
        tblLayout = (TableLayout) findViewById(R.id.tvDataTableLayout);


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        addColumn("aaaa","bbbb2");
        addColumn("aaab","bbbb3");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
        addColumn("aaac","bbbb4");
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

    public void salir() {
        finish();
    }
}
