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

public class Wednesday extends Activity implements
        OnItemSelectedListener {

    Calendar dateTime = Calendar.getInstance();
    Spinner spinner1,spinner2,spinner3,spinner4/*,spinner5,spinner6,spinner7,spinner8*/;
    TextView ts1,ts2,ts3,ts4/*,ts5,ts6,ts7,ts8*/,te1,te2,te3,te4/*,te5,te6,te7,te8*/;
    Button btnnext;
    EditText inputLabel;
    List<String> subject_names;
    String FinalResult;
    String time_startx1,time_endx1,time_startx2,time_endx2,time_startx3,time_endx3,time_startx4,time_endx4;
    String label;// = new String[8];
    public String email_holder="initial",email_holderx;
    public String day_holder = "Wednesday";

    String HttpUrl = "http://10.50.33.206/VirtualAttendanceTracker/G/InsertTimeTable.php";

    ProgressDialog progressDialog;
    HashMap<String,String> hashMap1 = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wednesday);

        btnnext = (Button)findViewById(R.id.next_button_wednesday) ;
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_holder=="initial"){

                }
                else {
                    Intent x = new Intent(Wednesday.this, Thursday.class);
                    x.putExtra("email_thu", email_holder);
                    startActivity(x);
                }
            }
        });



        Intent xk = getIntent();
        email_holder = xk.getStringExtra("email_wed");


        // Spinner element
        spinner1 = (Spinner) findViewById(R.id.spinner1_wed);
        spinner2 = (Spinner) findViewById(R.id.spinner2_wed);
        spinner3 = (Spinner) findViewById(R.id.spinner3_wed);
        spinner4 = (Spinner) findViewById(R.id.spinner4_wed);
        /*spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        spinner8 = (Spinner) findViewById(R.id.spinner8);*/


        ts1 = (TextView)findViewById(R.id.time_start1_wed);
        ts2 = (TextView)findViewById(R.id.time_start2_wed);
        ts3 = (TextView)findViewById(R.id.time_start3_wed);
        ts4 = (TextView)findViewById(R.id.time_start4_wed);
        /*ts5 = (TextView)findViewById(R.id.time_start5);
        ts6 = (TextView)findViewById(R.id.time_start6);
        ts7 = (TextView)findViewById(R.id.time_start7);
        ts8 = (TextView)findViewById(R.id.time_start8);*/


        te1 = (TextView)findViewById(R.id.time_end1_wed);
        te2 = (TextView)findViewById(R.id.time_end2_wed);
        te3 = (TextView)findViewById(R.id.time_end3_wed);
        te4 = (TextView)findViewById(R.id.time_end4_wed);
        /*te5 = (TextView)findViewById(R.id.time_end5);
        te6 = (TextView)findViewById(R.id.time_end6);
        te7 = (TextView)findViewById(R.id.time_end7);
        te8 = (TextView)findViewById(R.id.time_end8);*/



        spinner1.setOnItemSelectedListener(this);
        loadSpinnerData();
        spinner2.setOnItemSelectedListener(this);
        loadSpinnerData();
        spinner3.setOnItemSelectedListener(this);
        loadSpinnerData();
        spinner4.setOnItemSelectedListener(this);
        loadSpinnerData();
      /*  spinner5.setOnItemSelectedListener(this);
        loadSpinnerData();
        spinner6.setOnItemSelectedListener(this);
        loadSpinnerData();
        spinner7.setOnItemSelectedListener(this);
        loadSpinnerData();
        spinner8.setOnItemSelectedListener(this);
        loadSpinnerData();*/








        ts1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();

            }
        });
        updateTextLabel();



        ts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime2();

            }
        });
        updateTextLabel2();

        ts3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime3();

            }
        });
        updateTextLabel3();
        ts4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime4();

            }
        });
        updateTextLabel4();
     /*   ts5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime5();

            }
        });
        updateTextLabel5();
        ts6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime6();

            }
        });
        updateTextLabel6();
        ts7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime7();

            }
        });
        updateTextLabel7();
        ts8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime8();

            }
        });
        updateTextLabel8();*/
        te1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime9();



            }
        });
        updateTextLabel9();
        te2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime10();

            }
        });
        updateTextLabel10();
        te3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime11();

            }
        });
        updateTextLabel11();
        te4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime12();

            }
        });
        updateTextLabel12();
       /* te5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime13();

            }
        });
        updateTextLabel3();
        te6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime14();

            }
        });
        updateTextLabel14();
        te7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime15();

            }
        });
        updateTextLabel15();
        te8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime16();

            }
        });
        updateTextLabel16();*/




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
        spinner2.setAdapter(dataAdapter);
        spinner3.setAdapter(dataAdapter);
        spinner4.setAdapter(dataAdapter);
       /* spinner5.setAdapter(dataAdapter);
        spinner6.setAdapter(dataAdapter);
        spinner7.setAdapter(dataAdapter);
        spinner8.setAdapter(dataAdapter);*/

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        label = parent.getItemAtPosition(position).toString();




        // Showing selected spinner item
        /*Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }




    private void updateTextLabel(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_startx1 = ("0"+hours+":"+"0"+mins +":00");
            ts1.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_startx1 = ("0"+hours+":"+mins +":00");
            ts1.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            time_startx1 = (hours+":"+"0"+mins +":00");
            ts1.setText(hours+":"+"0"+mins);
        }
        else {
            time_startx1 = (hours + ":" + mins + ":00");
            ts1.setText(hours + ":" + mins);
        }



    }
    private void updateTime(){
        new TimePickerDialog(this,t,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel();



        }
    };



    //ts2

    private void updateTextLabel2(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_startx2 = ("0"+hours+":"+"0"+mins +":00");
            ts2.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_startx2 = ("0"+hours+":"+mins +":00");
            ts2.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            time_startx2 = (hours+":"+"0"+mins +":00");
            ts2.setText(hours+":"+"0"+mins);
        }
        else {
            time_startx2 = (hours + ":" + mins + ":00");
            ts2.setText(hours + ":" + mins);
        }


    }
    private void updateTime2(){
        new TimePickerDialog(this,t2,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel2();

        }
    };

    //ts3



    private void updateTextLabel3(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_startx3 = ("0"+hours+":"+"0"+mins +":00");
            ts3.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_startx3 = ("0"+hours+":"+mins +":00");
            ts3.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            time_startx3 = (hours+":"+"0"+mins +":00");
            ts3.setText(hours+":"+"0"+mins);
        }
        else {
            time_startx3 = (hours + ":" + mins + ":00");
            ts3.setText(hours + ":" + mins);

        }

    }
    private void updateTime3(){
        new TimePickerDialog(this,t3,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t3 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel3();

        }
    };



    //ts4

    private void updateTextLabel4(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_startx4 = ("0"+hours+":"+"0"+mins +":00");
            ts4.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_startx4 = ("0"+hours+":"+mins +":00");
            ts4.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            time_startx4 = (hours+":"+"0"+mins +":00");
            ts4.setText(hours+":"+"0"+mins);
        }
        else {
            time_startx4 = (hours + ":" + mins + ":00");
            ts4.setText(hours + ":" + mins);
        }


    }
    private void updateTime4(){
        new TimePickerDialog(this,t4,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t4 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel4();

        }
    };




    //ts5

   /* private void updateTextLabel5(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            ts5.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            ts5.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            ts5.setText(hours+":"+"0"+mins);
        }
        else
            ts5.setText(hours+":"+mins);



    }
    private void updateTime5(){
        new TimePickerDialog(this,t5,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t5 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel5();

        }
    };





    //ts6

    private void updateTextLabel6(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            ts6.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            ts6.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            ts6.setText(hours+":"+"0"+mins);
        }
        else
            ts6.setText(hours+":"+mins);



    }
    private void updateTime6(){
        new TimePickerDialog(this,t6,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t6 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel6();

        }
    };




    //ts7

    private void updateTextLabel7(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            ts7.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            ts7.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            ts7.setText(hours+":"+"0"+mins);
        }
        else
            ts7.setText(hours+":"+mins);



    }
    private void updateTime7(){
        new TimePickerDialog(this,t7,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t7 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel7();

        }
    };





    //ts8

    private void updateTextLabel8(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            ts8.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            ts8.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            ts8.setText(hours+":"+"0"+mins);
        }
        else
            ts8.setText(hours+":"+mins);



    }
    private void updateTime8(){
        new TimePickerDialog(this,t8,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t8 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel8();

        }
    };*/



    //te2

    private void updateTextLabel10(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_endx2 = ("0"+hours+":"+"0"+mins + ":00");
            te2.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_endx2 = ("0"+hours+":"+mins + ":00");
            te2.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            time_endx2 = (hours+":"+"0"+mins + ":00");
            te2.setText(hours+":"+"0"+mins);
        }
        else {
            time_endx2 = (hours + ":" + mins + ":00");
            te2.setText(hours + ":" + mins);
        }
        InsertIntoTable(email_holder, "Wednesday", label, time_startx2, time_endx2, HttpUrl);



    }
    private void updateTime10(){
        new TimePickerDialog(this,t10,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t10 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel10();

        }
    };



    //te1

    private void updateTextLabel9(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_endx1 = ("0"+hours+":"+"0"+mins + ":00");
            //time_startx1 = ("0"+hours+":"+"0"+mins +":00");
            te1.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_endx1 = ("0"+hours+":"+mins + ":00");
            te1.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){

            time_endx1 = (hours+":"+"0"+mins + ":00");
            te1.setText(hours+":"+"0"+mins);
        }
        else {
            time_endx1 = (hours+":"+mins + ":00");
            time_endx1 = (hours+":"+mins + ":00");
            te1.setText(hours + ":" + mins);
        }


        InsertIntoTable(email_holder, "Wednesday", label, time_startx1, time_endx1, HttpUrl);


    }
    private void updateTime9(){
        new TimePickerDialog(this,t9,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t9 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel9();

            //InsertIntoTable(email_holder,day_holder,label,time_startx1,time_endx1);
            //InsertIntoTable(email_holder,day_holder,label,time_startx1,time_endx1);


        }

    };





    //te3

    private void updateTextLabel11(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_endx3 = ("0"+hours+":"+"0"+mins + ":00");
            te3.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_endx3 = ("0"+hours+":"+mins + ":00");
            te3.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            time_endx3 = (hours+":"+"0"+mins + ":00");
            te3.setText(hours+":"+"0"+mins);
        }
        else {
            time_endx3 = (hours + ":" + mins + ":00");
            te3.setText(hours + ":" + mins);
        }
        InsertIntoTable(email_holder, "Wednesday", label, time_startx3, time_endx3, HttpUrl);

    }
    private void updateTime11(){
        new TimePickerDialog(this,t11,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t11 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel11();

        }
    };





    //te4

    private void updateTextLabel12(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            time_endx4 = ("0"+hours+":"+"0"+mins + ":00");
            te4.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            time_endx4 = ("0"+hours+":"+mins + ":00");
            te4.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            time_endx4 = (hours+":"+"0"+mins + ":00");
            te4.setText(hours+":"+"0"+mins);
        }
        else {
            time_endx4 = (hours + ":" + mins + ":00");
            te4.setText(hours + ":" + mins);

        }
        InsertIntoTable(email_holder, "Wednesday", label, time_startx4, time_endx4, HttpUrl);

    }
    private void updateTime12(){
        new TimePickerDialog(this,t12,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t12 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel12();

        }
    };




    //te5

    /*private void updateTextLabel13(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            te5.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            te5.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            te5.setText(hours+":"+"0"+mins);
        }
        else
            te5.setText(hours+":"+mins);



    }
    private void updateTime13(){
        new TimePickerDialog(this,t13,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t13 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel13();

        }
    };


    //te6

    private void updateTextLabel14(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            te6.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            te6.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            te6.setText(hours+":"+"0"+mins);
        }
        else
            te6.setText(hours+":"+mins);



    }
    private void updateTime14(){
        new TimePickerDialog(this,t14,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t14 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel14();

        }
    };



    //te7

    private void updateTextLabel15(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            te7.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            te7.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            te7.setText(hours+":"+"0"+mins);
        }
        else
            te7.setText(hours+":"+mins);



    }
    private void updateTime15(){
        new TimePickerDialog(this,t15,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t15 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel15();

        }
    };





    //te8

    private void updateTextLabel16(){
        int hours = dateTime.get(dateTime.HOUR_OF_DAY);
        int mins = dateTime.get(dateTime.MINUTE);
        if(hours<10 && mins<10)
        {
            te8.setText("0"+hours+":"+"0"+mins);
        }
        else if (hours<10 && mins>10)
        {
            te8.setText("0"+hours+":"+mins);
        }
        else if(hours>10 && mins<10){
            te8.setText(hours+":"+"0"+mins);
        }
        else
            te8.setText(hours+":"+mins);




    }
    private void updateTime16(){
        new TimePickerDialog(this,t16,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    TimePickerDialog.OnTimeSetListener t16 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel16();

        }
    };*/





    public void InsertIntoTable(final String Email,final String Day,final String Subject,final String Time_Start, final String Time_End,final String url4webService){
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
                    Toast.makeText(Wednesday.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                }


            }

            @Override
            protected String doInBackground(String... params) {
                hashMap1.put("Email",params[0]);
                hashMap1.put("Day",params[1]);

                hashMap1.put("Subject",params[2]);


                hashMap1.put("Time_Start",params[3]);
                hashMap1.put("Time_End",params[4]);





                FinalResult = httpParse.postRequest(hashMap1, url4webService);

                return FinalResult;
            }
        }

        InsertIntoTableClass insertIntoTableClass = new InsertIntoTableClass();

        insertIntoTableClass.execute(Email,Day,Subject,Time_Start,Time_End,url4webService);
    }









}
