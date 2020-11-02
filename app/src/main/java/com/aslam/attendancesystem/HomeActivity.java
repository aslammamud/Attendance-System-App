package com.aslam.attendancesystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private Button checkRecords, myProfile, attendance;
    private TextView show;
    private ImageView logout;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        show = findViewById(R.id.show);
        checkRecords = findViewById(R.id.checkRecords);
        myProfile = findViewById(R.id.myProfile);
        attendance = findViewById(R.id.attendance);
        logout = findViewById(R.id.logout);

        myRef = FirebaseDatabase.getInstance().getReference("Employee").child("9585");
        showHeaderProfName();

        Paper.init(this);

        //This button will show every user profile information from database
        checkRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(HomeActivity.this, dataDisplay.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this, "Error in Android App Manifest!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //This button will show particular data from database
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                startActivity(intent);
                //show.setVisibility(View.VISIBLE);
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Attendance.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }

            private void showPopup() {
                AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
                alert.setMessage("Are you sure?")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Paper.book().write(Prevalent.UserIdKey, "id");
                                Paper.book().write(Prevalent.UserPasswordKey, "pass");
                                logout();
                            }
                        }).setNegativeButton("Cancel", null);

                AlertDialog alert1 = alert.create();
                alert1.show();
            }

            private void logout() {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void showHeaderProfName() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String Name = (String) snapshot.child("name").getValue().toString();
                show.setText(Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
