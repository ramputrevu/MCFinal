package com.example.mcfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class Main5Activity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Spinner spinner;
    String username, returnDescTxt;
    String double_qoute = "\"";
    TextView returnDescription;
    int product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Bundle extras = getIntent().getExtras();

        databaseHelper = new DatabaseHelper(Main5Activity.this);
        spinner = (Spinner) findViewById(R.id.products);
        returnDescription = (TextView) findViewById(R.id.returnDescription);
        TextView submit = (TextView) findViewById(R.id.submit_return);

        if(extras != null) {
            username = extras.getString("username");
        }

        // get a cursor from the Db
        Cursor c = databaseHelper.openDatabase().rawQuery("SELECT loan_id, equipment_loan.product_id, product_description, loan_to FROM equipment_loan, products WHERE equipment_loan.product_id = products.product_id AND (returned=0 or returned is NULL) and loan_to ="+double_qoute+username+double_qoute, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(c.moveToFirst()) {
            do{
                String word = c.getString(c.getColumnIndexOrThrow("loan_id"));
                spinnerContent.add(word);
            }while(c.moveToNext());
        }
        c.close();
        if (c.getCount() == 0) {
            returnDescription.setText("No loans to return");
            submit.setEnabled(FALSE);
            spinner.setEnabled(FALSE);
        }

        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Main5Activity.this,android.R.layout.simple_spinner_item, allSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c2 = databaseHelper.openDatabase().rawQuery("SELECT loan_id, equipment_loan.product_id, product_description, loan_to, loan_date FROM equipment_loan, products WHERE equipment_loan.product_id = products.product_id AND loan_id ="+spinner.getSelectedItem(), null);
                if (c2.getCount() > 0) {
                    c2.moveToNext();
                    returnDescTxt = "Product description: \n"+c2.getString(c2.getColumnIndexOrThrow("product_description")) + "\n\nLoan date: "+ c2.getString(c2.getColumnIndexOrThrow("loan_date"));
                    product_id = c2.getInt(c2.getColumnIndexOrThrow("product_id"));
                    returnDescription.setText(returnDescTxt);
                }
                c2.close();
                return;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                returnDescription.setText("");
            }
        });

    }
    public void onClickFindProduct(View view){
        if (spinner.getSelectedItem() != null) {
            TextView submit = (TextView) findViewById(R.id.submit_return);
            String sql_update = "UPDATE equipment_loan SET returned = 1 WHERE loan_id =" + spinner.getSelectedItem();
            String sql_update_stock = "UPDATE products SET product_qty = product_qty + 1 WHERE product_id=" + product_id;
            databaseHelper.openDatabase().execSQL(sql_update);
            databaseHelper.openDatabase().execSQL(sql_update_stock);
            submit.setText("Thank you. \nReturn has been submitted");
            submit.setEnabled(FALSE);
        } else {
            Toast.makeText(this, "Please select a return from the list", Toast.LENGTH_SHORT).show();
        }
    }
}
