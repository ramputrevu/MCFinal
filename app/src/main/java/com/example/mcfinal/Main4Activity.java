package com.example.mcfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    TextView confirmationTxt;
    DatabaseHelper databaseHelper;
    int loan_id = 1;
    String double_qoute = "\"";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Calling database class
        databaseHelper = new DatabaseHelper(Main4Activity.this);

        // fetch the passed on information (username, product_type, and product_id)
        Bundle extras = getIntent().getExtras();
        String username, product_type, product_id, loan_to, loan_date;
        username = product_type = product_id = loan_to = loan_date = null;
        if(extras != null) {
            username = extras.getString("username");
            product_type = extras.getString("product_type");
            product_id = extras.getString("product_id");
//            Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
//            intent.putExtra("username", username);
//            intent.putExtra("product_type", product_type);
        }

        // get the latest (max) loan_id from the database then add 1 to it (new loan_id)
        Cursor c = databaseHelper.openDatabase().rawQuery("SELECT DISTINCT loan_id FROM equipment_loan", null);
        if (c.getCount() > 0) {
            c.moveToLast();
            loan_id = c.getInt(c.getColumnIndexOrThrow("loan_id")) + 1;
        }
        c.close();
        databaseHelper.close();

        // insert the new loan information to the Db
        String sql_insert = "INSERT INTO equipment_loan(loan_id, product_id, loan_to, loan_date) values ("+loan_id+ ", "+ product_id + ", "+ double_qoute + username + double_qoute +", CURRENT_DATE)";
        databaseHelper.openDatabase().execSQL(sql_insert);

        // Reduce the quantity of the product in the Db
        String sql_update_qty = "UPDATE products SET product_qty = product_qty - 1 where product_id="+product_id;
        databaseHelper.openDatabase().execSQL(sql_update_qty);

        // confirm that the loan information is stored in the Db by getting the loan information from the Db directly
        Cursor c2 = databaseHelper.openDatabase().rawQuery("SELECT DISTINCT loan_id, product_id, loan_to, loan_date FROM equipment_loan where loan_id ="+loan_id, null);
        if (c2.getCount() > 0) {
            c2.moveToNext();
            loan_id = c2.getInt(c2.getColumnIndexOrThrow("loan_id"));
            product_id = c2.getString(c2.getColumnIndexOrThrow("product_id"));
            loan_to = c2.getString(c2.getColumnIndexOrThrow("loan_to"));
            loan_date = c2.getString(c2.getColumnIndexOrThrow("loan_date"));
        }
        c2.close();

        confirmationTxt = (TextView) findViewById(R.id.checkoutDetailsTxt);

        // change the TextView to the loan information text
        String message = "loan #" + loan_id + "\nLoaned to: " + loan_to +"\nProduct id is: " + product_id + "\nLoan Date: " + loan_date;
        confirmationTxt.setText(message);

    }
}
