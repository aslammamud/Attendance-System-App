package com.aslam.attendancesystem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Attendance extends AppCompatActivity {

    DatabaseReference myRef;
    Students students;
    DatePickerDialog picker;
    private CheckBox student1, student2, student3, student4;
    private Button saveAttendance, cancelAttendance;
    private EditText selectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        myRef = FirebaseDatabase.getInstance().getReference("Attendances");

        student1 = findViewById(R.id.student1);
        student2 = findViewById(R.id.student2);
        student3 = findViewById(R.id.student3);
        student4 = findViewById(R.id.student4);
        saveAttendance = findViewById(R.id.saveAttendance);
        cancelAttendance = findViewById(R.id.cancelAttendance);

        selectDate = (EditText) findViewById(R.id.selectDate);
        selectDate.setInputType(InputType.TYPE_NULL);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Attendance.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        cancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = selectDate.getText().toString();

                if (Date.matches("")) {
                    Toast.makeText(Attendance.this, "Enter Date Please!", Toast.LENGTH_SHORT).show();
                } else {
                    students = new Students();
                    String Student1 = student1.getText().toString();
                    String Student2 = student2.getText().toString();
                    String Student3 = student3.getText().toString();
                    String Student4 = student4.getText().toString();


                    if (student1.isChecked()) {
                        students.setStudent1(Student1);
                        myRef.child(Date).setValue(students);
                    } else {
                    }
                    if (student2.isChecked()) {
                        students.setStudent2(Student2);
                        myRef.child(Date).setValue(students);
                    } else {
                    }
                    if (student3.isChecked()) {
                        students.setStudent3(Student3);
                        myRef.child(Date).setValue(students);
                    } else {
                    }
                    if (student4.isChecked()) {
                        students.setStudent4(Student4);
                        myRef.child(Date).setValue(students);
                    } else {
                    }

                    finish();
                    Toast.makeText(Attendance.this, "Attendance updated!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
