package com.example.mcfinal;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Main3Activity extends Activity {

    RadioButton Radiobtn;
    Button checkoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button checkoutBtn = (Button) findViewById(R.id.checkoutbutton);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                String username, product_type;
                String product;
                username = product_type = null;
                Radiobtn = (RadioButton) findViewById(R.id.Dell);
                if(extras != null) {
                    username = extras.getString("username");
                    product_type = extras.getString("product_type");
                    Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("product_type", product_type);
                    if(Radiobtn.isChecked()){
                        intent.putExtra("product", "Dell");
                    } else {intent.putExtra("product", "Mac");}
                    startActivity(intent);
                }
            }

        });
    }
}