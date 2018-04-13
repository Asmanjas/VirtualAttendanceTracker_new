package com.aptech.asmanjas.virtualattendancetracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class enter_past_attendance extends Activity implements
        OnItemSelectedListener {

    Calendar dateTime = Calendar.getInstance();
    Spinner spinner1,spinner2,spinner3,spinner4/*,spinner5,spinner6,spinner7,spinner8*/;
    EditText class_attended1,class_attended2,ts3,ts4/*,ts5,ts6,ts7,ts8*/,total_classes1,total_classes2,te3,te4/*,te5,te6,te7,te8*/;
    Button btnnext,update_attendance1,update_attendance2;
    EditText inputLabel;
    List<String> subject_names;
    String class_attended1_holder,class_attended2_holder,total_classes1_holder,total_classes2_holder;
    String FinalResult;

    String label;// = new String[8];
    public String email_holder="initial";



    String HttpUrl = "http://192.168.0.102/VirtualAttendanceTracker/G/updatePastAttendance.php";

    ProgressDialog progressDialog;
    HashMap<String,String> hashMap1 = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_past_attendance);


        Intent xk = getIntent();
        email_holder = xk.getStringExtra("email_fri1");

        btnnext = (Button)findViewById(R.id.next_button_upAttendance2) ;
        update_attendance1  =(Button)findViewById(R.id.update1_btn_update_attemdance);




        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_holder=="initial"){

                }
                else {
                    Intent x = new Intent(enter_past_attendance.this, enterDetailsG.class);
                    startActivity(x);
                }
            }
        });

update_attendance1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        class_attended1_holder = class_attended1.getText().toString();
        total_classes1_holder = total_classes1.getText().toString();
        updateAttendance(email_holder,label,class_attended1_holder,total_classes1_holder,HttpUrl);
        class_attended1.setText("");
        total_classes1.setText("");
        //checkEditTextIsEmptyorNot();
    }
});



        // Spinner element
        spinner1 = (Spinner) findViewById(R.id.spinner1_past);




        class_attended1= (EditText) findViewById(R.id.time_start1_past);



        total_classes1 = (EditText) findViewById(R.id.time_end1_past);




        spinner1.setOnItemSelectedListener(this);
        loadSpinnerData();


    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
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
        spinner1.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        label = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    public void updateAttendance(final String email,final String subject,final String class_attended, final String total_classes,final String url4webService){
        class InsertIntoTableClass extends AsyncTask<String,Void,String > {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //Dialog = ProgressDialog.show(Tuesday.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //progressDialog.dismiss();

                if(httpResponseMsg=="") {

                }
                else{
                    Toast.makeText(enter_past_attendance.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                }


            }

            @Override
            protected String doInBackground(String... params) {
                hashMap1.put("email",params[0]);
                hashMap1.put("subject",params[1]);

                hashMap1.put("attendance",params[2]);


                hashMap1.put("total_attendance",params[3]);






                FinalResult = httpParse.postRequest(hashMap1, url4webService);

                return FinalResult;
            }
        }

        InsertIntoTableClass insertIntoTableClass = new InsertIntoTableClass();

        insertIntoTableClass.execute(email,subject,class_attended,total_classes,url4webService);
    }









}

