package com.example.mcfinal;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


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

        // Calling database class
        databaseHelper = new DatabaseHelper(MainActivity.this);

        // Login button on click
        btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // check if user exists in Db
                    boolean isExist = databaseHelper.checkUserExist(edtEmail.getText().toString(), edtPassword.getText().toString());
                    // if yes, move to screen 2 and pass on the username
                    if(isExist){
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("username", edtEmail.getText().toString());
                        startActivity(intent);
                    }
                    // If not, show error message
                    else {
                        edtPassword.setText(null);
                        Toast.makeText(MainActivity.this, "Login failed. Invalid username or password.", Toast.LENGTH_SHORT).show();
                    }
                }
//                Intent myIntent = new Intent(view.getContext(), Main2Activity.class);
//                startActivityForResult(myIntent, 0);

        });
    }
}
