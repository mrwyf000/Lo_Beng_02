package com.example.carparkmainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DriverLogin extends AppCompatActivity {

    private EditText userPassword, userEmail;
    private Button loginButton;
    private TextView userRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        userPassword = (EditText) findViewById(R.id.edUserPassword);
        userEmail = (EditText) findViewById(R.id.edUserEmail);
        loginButton = (Button) findViewById(R.id.btLogin);
        userRegistration = (TextView) findViewById(R.id.tvRegistration);

        //userRegistration.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(DriverLogin.this, DriverRegistrationActivity.class);
        //       startActivity(intent);
        //   }
        //});
    }
}
