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

public class SignUpG extends AppCompatActivity {

    Button signup,login;
    EditText first_name,last_name,Email_Address,Password,REpassword;
    String first_name_holder,last_name_holder,Email_address_holder,Password_holder,REpassword_holder;
    String FinalResult;
    String HttpUrl = "http://192.168.0.102/VirtualAttendanceTracker/G/gSignUp.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String user_email = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_g);
        first_name = (EditText)findViewById(R.id.first_name_signup_g);
        last_name = (EditText)findViewById(R.id.last_name_signup_g);
        Email_Address = (EditText)findViewById(R.id.email_address_signup_g);
        Password = (EditText)findViewById(R.id.password_signup_g);
        REpassword = (EditText)findViewById(R.id.repassword_signup_g);
        signup = (Button)findViewById(R.id.signup_button_signupg);
        login = (Button)findViewById(R.id.login_button_signup_g);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent y = new Intent(SignUpG.this,gLoginScreenActivity.class);
                startActivity(y);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    USERRegistrationFunction(Email_address_holder,first_name_holder,last_name_holder,Password_holder,REpassword_holder);
                }
                else
                {
                    Toast.makeText(SignUpG.this,"please fill all the fields",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){
        first_name_holder = first_name.getText().toString();
        last_name_holder = last_name.getText().toString();
        Email_address_holder = Email_Address.getText().toString();
        Password_holder = Password.getText().toString();
        REpassword_holder = REpassword.getText().toString();

        if(TextUtils.isEmpty(first_name_holder) ||TextUtils.isEmpty(last_name_holder) || TextUtils.isEmpty(Email_address_holder) || TextUtils.isEmpty(Password_holder) || TextUtils.isEmpty(REpassword_holder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void USERRegistrationFunction(final String email,final String F_Name,final String L_Name,final String Password,final String Repassword){
        class UserRegisterFunctionClass extends AsyncTask<String,Void,String >{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignUpG.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(SignUpG.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                Intent z= new Intent(SignUpG.this,enterDetailsG.class);
                //z.putExtra(user_email,email);
                Intent xk = new Intent(SignUpG.this,Monday.class);
                xk.putExtra("user_email",Email_address_holder);
                startActivity(z);

            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("email",params[0]);
                hashMap.put("F_name",params[1]);

                hashMap.put("L_name",params[2]);



                hashMap.put("password",params[3]);




                FinalResult = httpParse.postRequest(hashMap, HttpUrl);

                return FinalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(email,F_Name,L_Name,Password,Repassword);
    }


}

