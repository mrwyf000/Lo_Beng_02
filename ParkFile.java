package com.example.carparkmainmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class ParkFile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Button logout, submit;
    private EditText parkName, parkAddress, motor, privateCar, truck, parkingFee, flexiblePriceFee;
    private CheckBox flexiblePricing, mcHalfHour, mcOneHour, mcTwoHour;
    String park_name, park_address, motor_Car, private_Car, truck_Car, parking_Fee, halfhr, onehr, twohr, flexible_Pricing, flexible_Fee;

    private void setupUIViews() {
        parkName = (EditText) findViewById(R.id.edParkName);
        parkAddress = (EditText) findViewById(R.id.edCarParkAddress);
        motor = (EditText) findViewById(R.id.edMotorcycle);
        privateCar = (EditText) findViewById(R.id.edPrivateCar);
        truck = (EditText) findViewById(R.id.edTruck);
        parkingFee = (EditText) findViewById(R.id.edParkingFee);
        flexiblePriceFee = (EditText) findViewById(R.id.edFlexiblePricingFee);
        flexiblePricing = (CheckBox) findViewById(R.id.cbFlexiblePricing);
        mcHalfHour = (CheckBox) findViewById(R.id.cbMCHalfHour);
        mcOneHour = (CheckBox) findViewById(R.id.cbMCOneHour);
        mcTwoHour = (CheckBox) findViewById(R.id.cbMCTwoHour);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_file);
        setupUIViews();

        submit = (Button) findViewById(R.id.btSubmit);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        logout = (Button) findViewById(R.id.btLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ParkFile.this, "upload successful", Toast.LENGTH_SHORT).show();
            }
        });

    }





    private void Logout() {
        firebaseAuth.signOut();
        finish();
        Toast.makeText(ParkFile.this, "Logout Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ParkFile.this, CarParkLogin.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
