package com.example.mcfinal;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.mcfinal.Main3Activity;
import com.example.mcfinal.R;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button next = (Button) findViewById(R.id.laptops);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Main3Activity.class);
                startActivityForResult(myIntent, 0);

            }

        });

        Button returns = (Button) findViewById(R.id.returns);
        returns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Main5Activity.class);
                startActivityForResult(myIntent, 0);

            }

        });

    }
}

