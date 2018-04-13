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

public class register_student extends AppCompatActivity {

    Button register,log_in;//back button not added yet
    EditText First_Name,Last_Name,Roll_Number,Password,RePassword;
    String F_Name_Holder,L_Name_Holder,Roll_Number_holder,Password_holder,RePassword_holder;
    String FinalResult,truncated_roll_number;
    String HttpUrl = "http://192.168.0.102/VirtualAttendanceTracker/StudentRegistration.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    int roll_number_integer;
    String lab_batch,theory_Batch,lab_batch_holder,theory_Batch_holder;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_student);

        //assigning id's
        First_Name = (EditText)findViewById(R.id.first_name_register_student);
        Last_Name = (EditText)findViewById(R.id.last_name_register_student);
        //last name is not present in the XML yet, leaving feild for that
        Roll_Number = (EditText)findViewById(R.id.roll_number_register_student);
        Password = (EditText) findViewById(R.id.password_register_student);
        RePassword = (EditText) findViewById(R.id.retype_password_register_student);


        register = (Button)findViewById(R.id.register_button_register_student);
        log_in = (Button)findViewById(R.id.login_button_register_student);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();




                if(CheckEditText)   {
                    truncated_roll_number = Roll_Number_holder.substring(Roll_Number_holder.length() - 3);
                    theory_Batch_holder = checkForTheoryBatch(truncated_roll_number);
                    lab_batch_holder = checkForLabBatch(truncated_roll_number);

                    UserRegisterFunction(F_Name_Holder,L_Name_Holder,Roll_Number_holder,Password_holder,RePassword_holder,theory_Batch_holder,lab_batch_holder);

                }
                else
                {
                    Toast.makeText(register_student.this,"Please fill all the fields.",Toast.LENGTH_LONG).show();

                }
            }
        });


        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(register_student.this,login_student.class);
                startActivity(intent);
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        F_Name_Holder = First_Name.getText().toString();
        L_Name_Holder = Last_Name.getText().toString();
        Roll_Number_holder = Roll_Number.getText().toString();
        Password_holder = Password.getText().toString();
        RePassword_holder = RePassword.getText().toString();

        if(TextUtils.isEmpty(F_Name_Holder) ||TextUtils.isEmpty(L_Name_Holder) || TextUtils.isEmpty(Roll_Number_holder) || TextUtils.isEmpty(Password_holder) || TextUtils.isEmpty(RePassword_holder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public String checkForLabBatch(String truncated_roll_number)
    {
        roll_number_integer = Integer.parseInt(truncated_roll_number);
        if(roll_number_integer>=1&&roll_number_integer<=30)
        {
           lab_batch = "L1";
        }
        else if(roll_number_integer>=31&&roll_number_integer<=60)
        {
            lab_batch = "L2";
        }
        else
            lab_batch = "L3";
        return lab_batch;
    }

    public String checkForTheoryBatch(String truncated_roll_number){
        roll_number_integer = Integer.parseInt(truncated_roll_number);
        if(roll_number_integer>=1&&roll_number_integer<=42)
        {
            theory_Batch = "T1";
        }
        else
            theory_Batch = "T2";
        return theory_Batch;
    }
    public void UserRegisterFunction(final String F_Name,final String L_Name, final String roll_number, final String password, final String re_password,final String th_batch,final String lb_batch){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(register_student.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(register_student.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("F_name",params[0]);

                hashMap.put("L_name",params[1]);

                hashMap.put("roll_number",params[2]);

                hashMap.put("password",params[3]);
                hashMap.put("re_password",params[4]);
                hashMap.put("th_batch",params[5]);
                hashMap.put("lb_batch",params[6]);



                FinalResult = httpParse.postRequest(hashMap, HttpUrl);

                return FinalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,L_Name,roll_number,password,re_password,th_batch,lb_batch);
    }


}
