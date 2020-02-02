package com.example.carparkmainmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class ParkRegistrationActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    private EditText userName, userEmail, userPassword, userPassword2;
    private Button regButton, backButton, checkEmail;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String name, email, password, password2;

    private void setupUIViews() {
        userName = (EditText) findViewById(R.id.edUserName);
        userEmail = (EditText) findViewById(R.id.edUserEmail);
        userPassword = (EditText) findViewById(R.id.edUserPassword);
        userPassword2 = (EditText) findViewById(R.id.edUserPassword2);
        regButton = (Button) findViewById(R.id.btRegister);
        backButton = (Button) findViewById(R.id.btBack);
        checkEmail = (Button) findViewById(R.id.btCheckEmail);

    }

    private boolean validate() {
        boolean result = false;

        name = userName.getText().toString();
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        password2 = userPassword2.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "please enter all the details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(this, "Password too weak", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password2.equals(password)) {
            Toast.makeText(this, "cannot confirm the password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            result = true;
        }

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkRegistrationActivity.this, CarParkLogin.class));
            }
        });

        checkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail.getText().toString().isEmpty()) {
                    Toast.makeText(ParkRegistrationActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                }else   {
                    checkEmail(userEmail.getText().toString());
                }
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(ParkRegistrationActivity.this, "Registration succeed, uploaded", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(ParkRegistrationActivity.this, ParkFile.class));
                                sendEmailVerification();
                            } else {
                                Toast.makeText(ParkRegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }


    public void checkEmail(String email) {
        firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                Boolean check = !task.getResult().getProviders().isEmpty();

                if (!check) {
                    Toast.makeText(getApplicationContext(), "You can use this Email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email already present", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmailVerification()    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(ParkRegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(ParkRegistrationActivity.this, CarParkLogin.class));
                    }else   {
                        Toast.makeText(ParkRegistrationActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendUserData()  {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        ParkUserProfile parkUserProfile = new ParkUserProfile(name, email);
        myRef.setValue(parkUserProfile);

    }

}
