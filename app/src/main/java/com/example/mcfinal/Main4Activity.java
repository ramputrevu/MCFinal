package com.example.mcfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    TextView confirmationTxt;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Bundle extras = getIntent().getExtras();
        String username, product_type, product;
        username = product_type = product = null;
        if(extras != null) {
            username = extras.getString("username");
            product_type = extras.getString("product_type");
            product = extras.getString("product");
//            Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
//            intent.putExtra("username", username);
//            intent.putExtra("product_type", product_type);
        }
        databaseHelper = new DatabaseHelper(Main4Activity.this);
        confirmationTxt = (TextView) findViewById(R.id.checkoutDetailsTxt);

        String message = "username is: " + username +"\nProduct type is: " + product_type + "\nProduct is: " + product;
        confirmationTxt.setText(message);
    }
}
