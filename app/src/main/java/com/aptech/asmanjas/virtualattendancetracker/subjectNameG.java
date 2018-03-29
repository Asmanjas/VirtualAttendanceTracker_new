package com.aptech.asmanjas.virtualattendancetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class subjectNameG extends Activity /*implements
        OnItemSelectedListener */{

    // Spinner element
    //Spinner spinner1;

    // Add button
    Button btnAdd,btndone;

    // Input text
    EditText inputLabel;
    String parsed_email_holder;
    public final String further_parsed_email = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_name_g);


        Intent k = getIntent();
        //parsed_email_holder = k.getStringExtra(enterDetailsG.email)

        // Spinner element
       // spinner1 = (Spinner) findViewById(R.id.spinner12);

        // add button
        btnAdd = (Button) findViewById(R.id.add_button_subject_name_g);
        btndone = (Button)findViewById(R.id.next_button);


        // new label input field
        inputLabel = (EditText) findViewById(R.id.subject_name_g_edittext);




        // Spinner click listener
       // spinner1.setOnItemSelectedListener(this);

        // Loading spinner data from database
       // loadSpinnerData();

        /**
         * Add new label button click listener
         * */
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String label = inputLabel.getText().toString();

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

                    // loading spinner with newly added data
                    //loadSpinnerData();
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
                startActivity(x);
            }
        });
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
  /* private void loadSpinnerData() {
        // database handler
        SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //spinner1.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }*/
}