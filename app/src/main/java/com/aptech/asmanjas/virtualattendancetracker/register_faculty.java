package com.aptech.asmanjas.virtualattendancetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Asmanjas on 08-03-2018.
 */

public class register_faculty extends AppCompatActivity {


    Button Register_Button,Log_In;//back button not added yet
    EditText Faculty_Name,Last_Name,Faculty_Subject,Faculty_Password,Faculty_RePassword,Batch_Name,Batch_Name_Lab;
    String Faculty_Name_Holder,L_Name_Holder,Faculty_Subject_Holder,Faculty_Password_holder,Faculty_RePassword_holder,Batch_Name_Holder,Batch_Name_Lab_Holder;
    String FinalResult;
    String HttpUrl = "http://192.168.0.102/VirtualAttendanceTracker/FacultyRegistration.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_faculty);

        //assigning id's
        Faculty_Name = (EditText)findViewById(R.id.register_name_register_faculty);
        Batch_Name_Lab = (EditText)findViewById(R.id.register_faculty_batch_lab_register_faculty2);

        //last name is not present in the XML yet, leaving field for that
        Faculty_Subject = (EditText)findViewById(R.id.register_subject_register_faculty);
        Batch_Name = (EditText)findViewById(R.id.register_faculty_batch_register_faculty);
        Faculty_Password = (EditText) findViewById(R.id.register_faculty_password);
        Faculty_RePassword = (EditText) findViewById(R.id.register_faculty_repassword);


        Register_Button = (Button)findViewById(R.id.register_button_faculty_register);
        Log_In = (Button)findViewById(R.id.login_button_faculty_register);

        Log_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_faculty.this,login_faculty.class);
                startActivity(intent);
            }
        });



      /* Register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();


                if(CheckEditText)   {
                    UserRegisterFunction(Faculty_Name_Holder,Faculty_Subject_Holder,Batch_Name_Holder,Faculty_Password_holder,Faculty_RePassword_holder);

                }
                else
                {
                    Toast.makeText(register_faculty.this,"Please fill all the fields.",Toast.LENGTH_LONG).show();

                }
            }
        });*/

      Register_Button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              CheckEditTextIsEmptyOrNot();

              if(CheckEditText) {
                  UserRegisterFunction(Faculty_Name_Holder,Batch_Name_Holder,Faculty_Subject_Holder,Faculty_Password_holder,Faculty_RePassword_holder,Batch_Name_Lab_Holder);
              }
              else
              {
                  Toast.makeText(register_faculty.this,"fill all the fields",Toast.LENGTH_LONG).show();
              }
          }
      });


        Log_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(register_faculty.this,login_faculty.class);
                startActivity(intent);
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        Faculty_Name_Holder = Faculty_Name.getText().toString();
        Batch_Name_Holder = Batch_Name.getText().toString();
        Batch_Name_Lab_Holder = Batch_Name_Lab.getText().toString();

        Faculty_Subject_Holder = Faculty_Subject.getText().toString();
        Faculty_Password_holder = Faculty_Password.getText().toString();
        Faculty_RePassword_holder = Faculty_RePassword.getText().toString();


        if(TextUtils.isEmpty(Faculty_Name_Holder) ||TextUtils.isEmpty(Batch_Name_Holder)|| TextUtils.isEmpty(Faculty_Subject_Holder)||TextUtils.isEmpty(Batch_Name_Lab_Holder) || TextUtils.isEmpty(Faculty_Password_holder) || TextUtils.isEmpty(Faculty_RePassword_holder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
    public void UserRegisterFunction(final String F_Name, final String F_Subject,final String B_name, final String F_Password, final String F_RePassword , final String B_name_Lab){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(register_faculty.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(register_faculty.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("Faculty_Name",params[0]);
                hashMap.put("Batch_Name",params[1]);
                hashMap.put("Faculty_Subject",params[2]);

                //hashMap.put("Batch_Name",params[1]);



                hashMap.put("Faculty_Password",params[3]);
                hashMap.put("Batch_Name_Lab",params[5]);

               hashMap.put("Faculty_RePassword",params[4]);

                FinalResult = httpParse.postRequest(hashMap, HttpUrl);

                return FinalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,F_Subject,B_name,F_Password,F_RePassword,B_name_Lab);
    }


}


