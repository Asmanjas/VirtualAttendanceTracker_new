package com.aptech.asmanjas.virtualattendancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    Button student,faculty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //assigning id's to the buttons
        student = (Button)findViewById(R.id.button_student_login);
        faculty = (Button)findViewById(R.id.button_faculty_login);


        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,login_student.class);
                startActivity(intent);
            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent open_faculty_login = new Intent(MainActivity.this,login_faculty.class);
                startActivity(open_faculty_login);
            }
        });
    }
}
