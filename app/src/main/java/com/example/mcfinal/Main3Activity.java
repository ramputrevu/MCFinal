package com.example.mcfinal;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Main3Activity extends Activity {

    RadioButton Radiobtn;
    Button checkoutBtn;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Calling database class
        databaseHelper = new DatabaseHelper(Main3Activity.this);

        Button checkoutBtn = (Button) findViewById(R.id.checkoutbutton);

        // on the click on the checkout button
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                String username, product_type;
                String product, product_id;
                int product_qty = 0;
                username = product_type = product_id = null;
                Radiobtn = (RadioButton) findViewById(R.id.Dell);
                if(extras != null) {
                    // get the passed on information (using getExtras)
                    username = extras.getString("username");
                    product_type = extras.getString("product_type");

                    // pass on the username and product_type
                    Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("product_type", product_type);

                    // if dell is selected, then set product_id to 1. Else (Mac), set it to 2
                    if(Radiobtn.isChecked()){
                        intent.putExtra("product_id", "1");
                        product_id = "1";
                    } else {
                        intent.putExtra("product_id", "2");
                        product_id ="2";
                    }
                    // Check the quantity available of the product from the Db
                    Cursor c = databaseHelper.openDatabase().rawQuery("SELECT DISTINCT product_qty FROM products WHERE product_id="+product_id, null);
                    if (c.getCount() > 0) {
                        c.moveToNext();
                        product_qty = c.getInt(c.getColumnIndexOrThrow("product_qty"));
                    }
                    c.close();

                    // if there's quantity available, move to the checkout screen
                    if (product_qty > 0) {
                        startActivity(intent);
                    }
                    // if no quantity is available, show an error message
                    else {
                        Toast.makeText(Main3Activity.this, "No quantity available from this product, please choose another product", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }
}