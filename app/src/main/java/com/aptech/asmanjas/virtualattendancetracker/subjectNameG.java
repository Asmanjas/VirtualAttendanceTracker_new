package com.aptech.asmanjas.virtualattendancetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class subjectNameG extends Activity /*implements
        OnItemSelectedListener */{

    // Spinner element
    //Spinner spinner1;

    // Add button
    Button btnAdd,btndone;
TextView tx_subject_names;
String[] subject_names = new String[10];

    // Input text

    EditText inputLabel;
    String parsed_email_holder;
    public final String further_parsed_email = "";
    int i=0;
    String url4dbAccess = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessStudentDetailsFromTEMPdb.php";
    String getUrl4dbInsert = "http://192.168.0.102/VirtualAttendanceTracker/G/InsertStudentDetails.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_name_g);


        Intent k = getIntent();
        parsed_email_holder = k.getStringExtra("email_holder");



        btnAdd = (Button) findViewById(R.id.add_button_subject_name_g);
        btndone = (Button)findViewById(R.id.next_button);
        tx_subject_names = (TextView)findViewById(R.id.text_view_subject_names_g);
        inputLabel = (EditText) findViewById(R.id.subject_name_g_edittext);





        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String label = inputLabel.getText().toString();
                subject_names[i] = label;
                i = i+1;
                tx_subject_names.setText(label);

                if (label.trim().length() > 0) {



                    // database handler
                    SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(
                            getApplicationContext());


                    // inserting new label into database
                    db.insertLabel(label);

                    // making input filed text to blank
                    inputLabel.setText("");

                    // Hiding the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter label name",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(subjectNameG.this,Monday.class);
                x.putExtra("email_id",parsed_email_holder);
                startActivity(x);
            }
        });
    }


}
