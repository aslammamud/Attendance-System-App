package com.aslam.attendancesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText EID, EPass;
    private CheckBox checkBox;
    private Button login;
    private ProgressDialog loadingBar;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EID = findViewById(R.id.EID);
        EPass = findViewById(R.id.EPass);
        loadingBar = new ProgressDialog(this);
        checkBox = findViewById(R.id.checkbox);
        login = findViewById(R.id.login);

        Paper.init(this);

        myRef = FirebaseDatabase.getInstance().getReference("Employee").child("9585");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmployee();
            }
        });

    }

    private void LoginEmployee() {
        final String EmpID = EID.getText().toString();
        final String EmpPass = EPass.getText().toString();

        if (TextUtils.isEmpty(EmpID)) {
            Toast.makeText(LoginActivity.this, "Please write your employee id...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(EmpPass)) {
            Toast.makeText(LoginActivity.this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccount(EmpID, EmpPass);
        }
    }

    private void AllowAccount(final String EmpID, final String EmpPass) {
        if (checkBox.isChecked()) {
            Paper.book().write(Prevalent.UserIdKey, EmpID);
            Paper.book().write(Prevalent.UserPasswordKey, EmpPass);
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String eid = (String) snapshot.child("id").getValue().toString();
                String epass = (String) snapshot.child("password").getValue().toString();

                if (EmpID.equals(eid)) {
                    if (EmpPass.equals(epass)) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Employee id incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

