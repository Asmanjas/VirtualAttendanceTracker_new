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

public class gLoginScreenActivity extends AppCompatActivity {

    EditText getEmail,getPassword;
    Button getLogIn,getSignUp;
    String email_holder,password_holder;
    String final_result;
    String HttpURL = "http://10.50.33.206/VirtualAttendanceTracker/G/gLogin.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String user_email = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_login_screen);

        getEmail = (EditText) findViewById(R.id.email_g_login_screen);
        getPassword = (EditText) findViewById(R.id.password_g_login_screen);
        getLogIn = (Button) findViewById(R.id.btn_login_activity_gloginscreen);
        getSignUp = (Button) findViewById(R.id.signup_g_login_screen);


        getSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(gLoginScreenActivity.this, SignUpG.class);
                startActivity(x);
            }
        });

        getLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    UserLoginFunction(email_holder, password_holder);
                } else {
                    Toast.makeText(gLoginScreenActivity.this, "please fill all the fields", Toast.LENGTH_LONG).show();
                    ;
                }
            }
        });
    }


        public void CheckEditTextIsEmptyOrNot(){
            email_holder = getEmail.getText().toString();
            password_holder = getPassword.getText().toString();
        if(TextUtils.isEmpty(email_holder) || TextUtils.isEmpty(password_holder)){
            CheckEditText = false;
        }
        else    {
            CheckEditText = true;
        }

    }

    public void UserLoginFunction(final String email,final String password){
            class UserLoginClass extends AsyncTask<String,Void,String >{
                protected void onPreExecute(){
                    super.onPreExecute();
                    progressDialog = progressDialog.show(gLoginScreenActivity.this,"loading data",null,true,true);
                }
                protected void onPostExecute(String httpResponseMsg)    {
                    super.onPostExecute(httpResponseMsg);
                    progressDialog.dismiss();
                    if(httpResponseMsg.equalsIgnoreCase("Data matched"))    {
                        finish();

                        Intent intent = new Intent(gLoginScreenActivity.this,mainModuleG.class);
                        intent.putExtra(user_email,email);
                        startActivity(intent);

                    }
                    else    {
                        Toast.makeText(gLoginScreenActivity.this,"Invalid details",Toast.LENGTH_LONG).show();
                    }

                }
                protected String doInBackground(String... params)   {
                    hashMap.put("email",params[0]);
                    hashMap.put("password",params[1]);
                    final_result = httpParse.postRequest(hashMap,HttpURL);
                    return final_result;
                }

            }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(email_holder,password_holder);
    }

}

