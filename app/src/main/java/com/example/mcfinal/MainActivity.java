package com.example.mcfinal;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.mcfinal.Main2Activity;
//import com.example.mcfinal.R;

public class MainActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    DatabaseHelper databaseHelper;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText) findViewById(R.id.EmailId);
        edtPassword = (EditText) findViewById(R.id.loginpassword);
        btnLogin = (Button) findViewById(R.id.signin);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isExist = databaseHelper.checkUserExist(edtEmail.getText().toString(), edtPassword.getText().toString());

                    if(isExist){
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("username", edtEmail.getText().toString());
                        startActivity(intent);
                    } else {
                        edtPassword.setText(null);
                        Toast.makeText(MainActivity.this, "Login failed. Invalid username or password.", Toast.LENGTH_SHORT).show();
                    }
                }
//                Intent myIntent = new Intent(view.getContext(), Main2Activity.class);
//                startActivityForResult(myIntent, 0);

        });
    }
}
