package com.aptech.asmanjas.virtualattendancetracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class subjectNameG extends Activity /*implements
        OnItemSelectedListener */{

    Button btnAdd,btndone,resetbtn;
    TextView tx_subject_names;
    List<String> subject_names = new ArrayList<String>();
    HashMap<String,String> hashMap = new HashMap<>();
    String abc = "";

    HttpParse httpParse = new HttpParse();
    String xyz;
    String FinalResult = "";
    String email_holder,first_name_holder,last_name_holder,password_holder,repassword_holder;

    ProgressDialog progressDialog;
    EditText inputLabel;
    String parsed_email_holder;
    public final String further_parsed_email = "";
    int i=0;
   // String url4dbAccess = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessStudentDetailsFromTEMPdb.php";
    String getUrl4dbInsert = "http://192.168.0.102/VirtualAttendanceTracker/G/InsertStudentDetails.php";
   // String HttpUrl = "http://192.168.0.102/VirtualAttendanceTracker/G/gSignUp.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_name_g);


        Intent x = getIntent();
        email_holder = x.getStringExtra("emailx");
        first_name_holder = x.getStringExtra("first_namex");
        last_name_holder = x.getStringExtra("last_namex");
        password_holder = x.getStringExtra("passwordx");

        //tx_subject_names.setText(email_holder + "is awesome");
        resetbtn = (Button)findViewById(R.id.button7_1);

        btnAdd = (Button) findViewById(R.id.add_button_subject_name_g);
        btndone = (Button)findViewById(R.id.next_button);
        tx_subject_names = (TextView)findViewById(R.id.text_view_subject_names_g);
        inputLabel = (EditText) findViewById(R.id.subject_name_g_edittext);


      resetbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              subject_names.clear();
              tx_subject_names.setText("");
          }
      });


        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String label = inputLabel.getText().toString();

               // tx_subject_names.setText(label);
                if (label.trim().length() > 0) {


                    subject_names.add(label);
                    xyz = "";
                    for(i=0;i < subject_names.size();i++){
                        xyz = xyz + "  " + subject_names.get(i);
                        //tx_subject_names.setText( subject_names.get(i) + " , ");

                    }
                    tx_subject_names.setText(xyz);

                    // database handler
                    SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(
                            getApplicationContext());


                    // inserting new label into database
                    db.insertLabel(label);

                    // making input filed text to blank
                    inputLabel.setText("");

                    // Hiding the keyboard
                    /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);*/

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter label name",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (Iterator<String> iterator = subject_names.iterator();iterator.hasNext();)
              {
                  USERRegistrationFunction(email_holder,first_name_holder,last_name_holder,password_holder,iterator.next(),getUrl4dbInsert);
                }
                Intent x = new Intent(subjectNameG.this,Monday.class);
                x.putExtra("email_id",email_holder);
                startActivity(x);


            }
        });
    }
    public void USERRegistrationFunction(final String email,final String first_name,final String last_name,final String password,final String subject,final String getUrl4dbInsert){
        class UserRegisterFunctionClass extends AsyncTask<String,Void,String >{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(subjectNameG.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(subjectNameG.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("email",params[0]);
                hashMap.put("first_name",params[1]);
                hashMap.put("last_name",params[2]);
                hashMap.put("password",params[3]);
                hashMap.put("subject",params[4]);
                FinalResult = httpParse.postRequest(hashMap, getUrl4dbInsert);
                return FinalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(email,first_name,last_name,password,subject,getUrl4dbInsert);
    }






















































































   /* public void getDataFromTempDATABASE(final String email_h, final String url4dbAccess)
    {
        class getDATAfromDBclass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    putDATAintoArrays(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    abc =httpParse.postRequest(hashMap, url4dbAccess);

                    URL url = new URL(url4dbAccess);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {

                        sb.append(json + "\n");

                    }
                    return abc;

                } catch (Exception e) {
                    return null;
                }
            }
        }
        getDATAfromDBclass getJSON = new getDATAfromDBclass();
        getJSON.execute(email_h,url4dbAccess);

    }
    public void putDATAintoArrays(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

           first_name = obj.getString("first_name");
           last_name = obj.getString("last_name");
           password = obj.getString("password");



        }
        tx_subject_names.setText(password);

        Iterator<String> iterator = subject_names.iterator();
        while (iterator.hasNext())
        {
            insertIntoRealDB(parsed_email_holder,first_name,last_name,password,iterator.next(),getUrl4dbInsert);
        }



    }



    public void insertIntoRealDB(final String email,final String first_name,final String last_name,final String password,final String subject,final String getUrl4dbInsert){
        class insertIntoRealDBclass extends AsyncTask<String,Void,String >{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(SignUpG.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //progressDialog.dismiss();

                Toast.makeText(subjectNameG.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("email",params[0]);
                hashMap.put("first_name",params[1]);

                hashMap.put("last_name",params[2]);



                hashMap.put("password",params[3]);
                hashMap.put("subject",params[4]);

                FinalResult = httpParse.postRequest(hashMap, getUrl4dbInsert);

                return FinalResult;
            }
        }

        insertIntoRealDBclass userRegisterFunctionClass = new insertIntoRealDBclass();

        userRegisterFunctionClass.execute(email,first_name,last_name,password,subject,getUrl4dbInsert);
    }*/

}
