package com.example.mcfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

public class Main5Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

    }
    public void onClickFindProduct(View view){
        TextView submit = (TextView) findViewById(R.id.submit_return);

        Spinner products = (Spinner) findViewById(R.id.products);

        String productType = String.valueOf(products.getSelectedItem());

        submit.setText("Thank you. Form has been submitted");
    }
}
