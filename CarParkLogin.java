package com.example.carparkmainmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CarParkLogin extends AppCompatActivity {

    private EditText Password, Email;
    private Button loginButton;
    private TextView userReg,forgotPassword;
    private FirebaseDatabase firebaseDatabase;
    String email, password;

    private FirebaseAuth firebaseAuth;

    private void setupUIViews(){
        Password = (EditText) findViewById(R.id.edUserPassword);
        Email = (EditText) findViewById(R.id.edUserEmail);
        loginButton = (Button) findViewById(R.id.btLogin);
        userReg = (TextView) findViewById(R.id.tvRegistration);
        forgotPassword = (TextView) findViewById(R.id.tvForgotPassword) ;
    }


    private boolean validateEmailPassword() {
        setupUIViews();
        boolean result = false;

        email = Email.getText().toString();
        password = Password.getText().toString().trim();

        if (password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            result = true;
        }

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_park_login);

        setupUIViews();
        firebaseDatabase = FirebaseDatabase.getInstance();

        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarParkLogin.this, ParkRegistrationActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();


        if(user != null){
            finish();
            startActivity(new Intent(CarParkLogin.this, ParkFile.class));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmailPassword()) {
                    validateLogin(Email.getText().toString(), Password.getText().toString());
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarParkLogin.this, ForgotPasswordActivity.class));
            }
        });
    }


    private void validateLogin (String userEmail, String userPassword) {

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(CarParkLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                }else {
                    Toast.makeText(CarParkLogin.this, "Login Failed, email or password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkEmailVerification()   {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag) {
            finish();
            Toast.makeText(CarParkLogin.this, "Login Succeed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CarParkLogin.this, ParkFile.class));
        }else   {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
