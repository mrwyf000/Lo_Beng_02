package com.example.carparkmainmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class takedata extends AppCompatActivity {
    private TextView a, b, c, d;
    private Button upload;
    private DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takedata);

        a = (TextView)findViewById(R.id.tvname);
        b = (TextView)findViewById(R.id.tvage);
        c = (TextView)findViewById(R.id.tvheight);
        d = (TextView)findViewById(R.id.tvph);
        upload = (Button)findViewById(R.id.btupload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("1").child("1");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String age = dataSnapshot.child("age").getValue().toString();
                        String height = dataSnapshot.child("height").getValue().toString();
                        String ph = dataSnapshot.child("ph").getValue().toString();
                        a.setText(name);
                        b.setText(age);
                        c.setText(height);
                        d.setText(ph);
                        Toast.makeText(takedata.this, "finished", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(takedata.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
