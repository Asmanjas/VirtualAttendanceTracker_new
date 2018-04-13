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

public class login_faculty extends AppCompatActivity {


    EditText ClassName,Faculty_name,Faculty_password;
    Button login,back;
    String Class_Name_Holder,Faculty_Name_Holder,Faculty_Password_Holder;
    String FinalResult;
    Boolean CheckEditText ;
    String HttpURL = "http://192.168.0.102/VirtualAttendanceTracker/FacultyLogin.php";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserFacultyName = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_faculty);

        //ClassName = (EditText)findViewById(R.id.login_branch_MCA);
        Faculty_name = (EditText)findViewById(R.id.faculty_name_login_faculty);
        Faculty_password = (EditText)findViewById(R.id.faculty_password_login_faculty);
        login = (Button)findViewById(R.id.login_button_login_faculty);
        back=(Button)findViewById(R.id.back_button_login_faculty);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_faculty.this,MainActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserLoginFunction(Faculty_Name_Holder,Faculty_Password_Holder);

                }
                else {

                    Toast.makeText(login_faculty.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        Faculty_Name_Holder = Faculty_name.getText().toString();
        Faculty_Password_Holder = Faculty_password.getText().toString();

        if(TextUtils.isEmpty(Faculty_Name_Holder) || TextUtils.isEmpty(Faculty_Password_Holder))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }

    public void UserLoginFunction(final String Faculty_name, final String Faculty_password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(login_faculty.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    finish();

                    Intent intent = new Intent(login_faculty.this, Module_faculty.class);

                    intent.putExtra(UserFacultyName,Faculty_name);

                    startActivity(intent);

                }
                else{

                    Toast.makeText(login_faculty.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("faculty_name",params[0]);

                hashMap.put("password",params[1]);

                FinalResult = httpParse.postRequest(hashMap, HttpURL);

                return FinalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(Faculty_name,Faculty_password);
    }


}
