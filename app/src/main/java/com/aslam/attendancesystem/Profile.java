package com.aslam.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    private TextView showMyProfile, nameText, idText, genderText, phoneText, emailText, addressText;
    private EditText name, id, phone, email, address;
    private RadioGroup radio;
    private RadioButton radioButton;
    private Button updateProfile, goBack, saveUpdatedProfile, cancelUpdating;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        updateProfile = findViewById(R.id.updateProfile);
        goBack = findViewById(R.id.goBack);
        saveUpdatedProfile = findViewById(R.id.saveUpdatedProfile);
        cancelUpdating = findViewById(R.id.cancelUpdating);

        showMyProfile = findViewById(R.id.showMyProfile);
        nameText = findViewById(R.id.nameText);
        idText = findViewById(R.id.idText);
        genderText = findViewById(R.id.radioText);
        phoneText = findViewById(R.id.phoneText);
        emailText = findViewById(R.id.emailText);
        addressText = findViewById(R.id.addressText);

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        radio = findViewById(R.id.radio);

        myRef = FirebaseDatabase.getInstance().getReference("Employee").child("9585");
        setProfileinfoToPageFromDatabase();

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText.setVisibility(View.GONE);
                idText.setVisibility(View.GONE);
                phoneText.setVisibility(View.GONE);
                emailText.setVisibility(View.GONE);
                addressText.setVisibility(View.GONE);
                genderText.setVisibility(View.GONE);

                name.setVisibility(View.VISIBLE);
                id.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                radio.setVisibility(View.VISIBLE);

                getProfile();

                saveUpdatedProfile.setVisibility(View.VISIBLE);
                cancelUpdating.setVisibility(View.VISIBLE);
                updateProfile.setVisibility(View.GONE);
                goBack.setVisibility(View.GONE);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveUpdatedProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioId = radio.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                HashMap<String, Object> updatedValues = new HashMap<>();

                updatedValues.put("name", name.getText().toString());
                updatedValues.put("id", id.getText().toString());
                updatedValues.put("gender", radioButton.getText().toString());
                updatedValues.put("phone", phone.getText().toString());
                updatedValues.put("email", email.getText().toString());
                updatedValues.put("address", address.getText().toString());

                myRef.updateChildren(updatedValues);
                finish();
                startActivity(getIntent());
                Toast.makeText(Profile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelUpdating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    private void getProfile() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String Name = (String) snapshot.child("name").getValue().toString();
                String ID = (String) snapshot.child("id").getValue().toString();
                //String Gender = (String) snapshot.child("gender").getValue().toString();
                String Phone = (String) snapshot.child("phone").getValue().toString();
                String Email = (String) snapshot.child("email").getValue().toString();
                String Address = (String) snapshot.child("address").getValue().toString();

                name.setText(Name);
                id.setText(ID);
                phone.setText(Phone);
                email.setText(Email);
                address.setText(Address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setProfileinfoToPageFromDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String Name = (String) snapshot.child("name").getValue().toString();
                String ID = (String) snapshot.child("id").getValue().toString();
                String Gender = (String) snapshot.child("gender").getValue().toString();
                String Phone = (String) snapshot.child("phone").getValue().toString();
                String Email = (String) snapshot.child("email").getValue().toString();
                String Address = (String) snapshot.child("address").getValue().toString();

                showMyProfile.setText(Name);
                nameText.setText(Name);
                idText.setText(ID);
                genderText.setText(Gender);
                phoneText.setText(Phone);
                emailText.setText(Email);
                addressText.setText(Address);
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
