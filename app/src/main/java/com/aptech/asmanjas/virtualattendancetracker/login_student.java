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

public class login_student extends AppCompatActivity {

    EditText roll_number,password;
    Button log_in,create_account,back;
    String roll_number_holder,password_holder;
    String final_result;
    String HttpURL = "http://10.50.33.206/VirtualAttendanceTracker/StudentLogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String user_roll_number = "";//user_roll_number2="";



    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_student);

        roll_number = (EditText) findViewById(R.id.roll_number_login_student);
        password = (EditText) findViewById(R.id.password_login_student);
        log_in = (Button) findViewById(R.id.go_button_login_student);
        back = (Button) findViewById(R.id.back_button_login_student);
        create_account = (Button) findViewById(R.id.create_account_login_student);



        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_student.this,register_student.class);
                startActivity(intent);
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    UserLoginFunction(roll_number_holder,password_holder);

                }
                else
                {
                    Toast.makeText(login_student.this,"please fill all the details.",Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_main = new Intent(login_student.this,MainActivity.class);
                startActivity(open_main);
            }
        });


    }

    public void CheckEditTextIsEmptyOrNot() {
        roll_number_holder = roll_number.getText().toString();
        password_holder = password.getText().toString();


        if(TextUtils.isEmpty(roll_number_holder) || TextUtils.isEmpty(password_holder)){
            CheckEditText = false;
        }
        else    {
            CheckEditText = true;
        }
    }


    public void UserLoginFunction(final String roll_number,final String password)
    {
        class UserLoginClass extends AsyncTask<String,Void,String> {

            protected void onPreExecute()   {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(login_student.this,"loading data",null,true,true);
            }


            protected void onPostExecute(String httpResponseMsg)    {
                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                if(httpResponseMsg.equalsIgnoreCase("Data matched"))    {
                    finish();
                    /*SharedPreferences settings = getSharedPreferences(user_roll_number, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("roll_number", roll_number_holder);
                    editor.commit();*/
                    Intent intent = new Intent(login_student.this,Module_student.class);
                    intent.putExtra(user_roll_number,roll_number);
                    startActivity(intent);
                    /*Intent intent10 = new Intent(login_student.this,showAttendance.class);
                    intent10.putExtra(user_roll_number2,roll_number);
                    */
                }
                else    {
                    Toast.makeText(login_student.this,"Invalid details",Toast.LENGTH_LONG).show();
                }

            }
            protected String doInBackground(String... params)   {
                hashMap.put("roll_number",params[0]);
                hashMap.put("password",params[1]);
                final_result = httpParse.postRequest(hashMap,HttpURL);
                return final_result;
            }

        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(roll_number,password);
    }

}
