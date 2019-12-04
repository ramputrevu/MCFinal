package com.example.mcfinal;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mcfinal.Main3Activity;
import com.example.mcfinal.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main2Activity extends Activity {
    DatabaseHelper databaseHelper;
    Spinner spinner;
    String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseHelper = new DatabaseHelper(Main2Activity.this);
        spinner = (Spinner) findViewById(R.id.spinner);

        // get a cursor from the Db
        Cursor c = databaseHelper.openDatabase().rawQuery("SELECT DISTINCT product_type FROM products", null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(c.moveToFirst()) {
            do{
                String word = c.getString(c.getColumnIndexOrThrow("product_type"));
                spinnerContent.add(word);
            }while(c.moveToNext());
        }
        c.close();

        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_spinner_item, allSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button next = (Button) findViewById(R.id.submit_category);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                String product_type = null;
                spinner = (Spinner) findViewById(R.id.spinner);
                product_type = spinner.getSelectedItem().toString();
                if(extras != null) {
                    username = extras.getString("username");
                    Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("product_type", product_type);
                    startActivity(intent);
                }
            }

        });

        Button returns = (Button) findViewById(R.id.returns);
        returns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    username = extras.getString("username");
                    Intent intent = new Intent(Main2Activity.this, Main5Activity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }

            }

        });

    }
}

