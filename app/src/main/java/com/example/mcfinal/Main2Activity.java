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

        // Calling database class
        databaseHelper = new DatabaseHelper(Main2Activity.this);

        spinner = (Spinner) findViewById(R.id.spinner);
        Button next = (Button) findViewById(R.id.submit_category);
        Button returns = (Button) findViewById(R.id.returns);

        // fetch product types from the Db into a cursor
        Cursor c = databaseHelper.openDatabase().rawQuery("SELECT DISTINCT product_type FROM products", null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(c.moveToFirst()) {
            do{
                String word = c.getString(c.getColumnIndexOrThrow("product_type"));
                spinnerContent.add(word);
            }while(c.moveToNext());
        }
        c.close();

        // get the data from the above array to the variable allSpinner
        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        // populate the spinner with the array variable allSpinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_spinner_item, allSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Do actions on spinner selection
        // Currently not needed
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                return;
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });


        // next button on click
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // get the passed information from the previous screen through getExtras
                Bundle extras = getIntent().getExtras();
                String product_type = null;
                spinner = (Spinner) findViewById(R.id.spinner);
                // get the spinner selection and set it to product_type
                product_type = spinner.getSelectedItem().toString();

                // pass on username and product_type to the next screen
                if(extras != null) {
                    username = extras.getString("username");
                    Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("product_type", product_type);
                    startActivity(intent);
                }
            }

        });


        returns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // get the passed information from the previous screen through getExtras
                Bundle extras = getIntent().getExtras();

                // pass on username to the return screen
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

