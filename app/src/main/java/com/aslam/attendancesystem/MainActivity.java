package com.aslam.attendancesystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeTeacher;
    private ProgressDialog loadingBar;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingBar = new ProgressDialog(this);

        welcomeTeacher = findViewById(R.id.welcomeTeacher);
        Paper.init(this);

        myRef = FirebaseDatabase.getInstance().getReference("Employee").child("9585");


        welcomeTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        String EmpID = Paper.book().read(Prevalent.UserIdKey);
        String EmpPass = Paper.book().read(Prevalent.UserPasswordKey);
        if (!EmpID.equals("") && !EmpPass.equals("")) {
            if (!TextUtils.isEmpty(EmpID) && !TextUtils.isEmpty(EmpPass)) {
                AllowAccount(EmpID, EmpPass);

            }
        }

    }

    private void AllowAccount(final String EmpID, final String EmpPass) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String eid = (String) snapshot.child("id").getValue().toString();
                String epass = (String) snapshot.child("password").getValue().toString();

                if (EmpID.equals(eid)) {
                    if (EmpPass.equals(epass)) {
                        loadingBar.setMessage("Logging in...");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();

    }
}

