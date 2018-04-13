package com.aptech.asmanjas.virtualattendancetracker;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.HashMap;

public class Module_faculty extends AppCompatActivity {
    String present_students_roll_numbers;
    String abc = "";
    String abc2 = "";
    String subject_db = "DBMS";
    HttpParse httpParse = new HttpParse();
    public String[] data,data2,trimmed_Roll_Number;
    String faculty_subject;
    int dd,yy,mm;
    final Calendar cal = Calendar.getInstance();
    String FinalResult;
    ProgressDialog progressDialog;

    HashMap<String,String> hashMap = new HashMap<>();
    TextView showdate,show_facuty_name,show_subject;
    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<Integer> mUserNotSelectedItems = new ArrayList<>();
    String[] SelectedItems;

    String faculty_name_holder;
    String url = "http://192.168.0.102/VirtualAttendanceTracker/AccessFacultyDetails.php?FacultyName=";
    String url2 = "http://192.168.0.102/VirtualAttendanceTracker/AccessStudentDetailsforAttendance.php?Subject=";
    String url3 = "http://192.168.0.102/VirtualAttendanceTracker/UpdateAttendanceTable.php";

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_faculty);


        showdate = (TextView)findViewById(R.id.date_faculty_module);

        dd = cal.get(Calendar.DAY_OF_MONTH);
        mm = cal.get(Calendar.MONTH);
        yy = cal.get(Calendar.YEAR);
// set current date into textview
        showdate.setText(new StringBuilder()
// Month is 0 based, just add 1
                .append(dd).append(" ").append("-").append(mm + 1).append("-")
                .append(yy));
        show_facuty_name = (TextView)findViewById(R.id.faculty_name_faculty_module);
        show_subject = (TextView)findViewById(R.id.subject_faculty_module);


        Intent intent = getIntent();
        faculty_name_holder = intent.getStringExtra(login_faculty.UserFacultyName);

        mOrder=(Button) findViewById(R.id.take_attendance_button_faculty_module);
        mItemSelected=(TextView) findViewById(R.id.tvItemSelected);







        downloadJSON(faculty_name_holder,url+faculty_name_holder);

        downloadJSON2(subject_db,url2 + subject_db);
        //checkedItems = new boolean[listItems.length];
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Module_faculty.this);
                mBuilder.setTitle(R.string.dialog_title);
                boolean[] checkItems;
                mBuilder.setMultiChoiceItems(listItems,checkedItems,new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked){
                        if(isChecked) {
                            if(! mUserItems.contains(position)) {
                                mUserItems.add(position);
                            }
                            else if(mUserItems.contains(position)){
                                mUserItems.remove(position);

                            }
                        }
                        /*if(!isChecked){
                            if(!mUserNotSelectedItems.contains(position)) {
                                mUserNotSelectedItems.add(position);
                            }
                            else if(mUserNotSelectedItems.contains(position)){
                                mUserNotSelectedItems.remove(position);

                            }

                        }*/
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int which) {


                        SelectedItems = new String[mUserItems.size()];
                        trimmed_Roll_Number = new String[mUserItems.size()];

                        for (int i=0; i<mUserItems.size(); i++) {
                            SelectedItems[i] = listItems[mUserItems.get(i)];
                            trimmed_Roll_Number[i] = SelectedItems[i].substring(0,8);
                            insertIntoTest(trimmed_Roll_Number[i],faculty_subject,url3);
                        }

                        // mItemSelected.setText("");*/



                     String item = "";
                        for (int i=0; i<mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];

                            if(i != mUserItems.size() -1) {
                                item = item + ", ";
                            }
                        }

                        mItemSelected.setText(item);




                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {
                        dialogInterface.dismiss();

                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(i = 0; i< checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        //listItems = //;getResources().getStringArray(R.array.student_name);

    }
    private void downloadJSON(final String faculty_name_holder,final String urlWebService) {

        class DownloadJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    //my code

                    //HttpClient httpclient = new DefaultHttpClient();
                    //HttpPost httppost = new HttpPost("http://192.168.0.101/VirtualAttendanceTracker/G/AccessStudentDetailsG.php?email=" + email);
                    //httpclient.execute(httppost);*/


                    // hashMap.put("password",params[1]);

                    /*FinalResult =*/
                    hashMap.put("faculty_name",params[0]);
                    abc =httpParse.postRequest(hashMap, urlWebService);


                    //return FinalResult;

                    //httpParse.postRequest(hashMap, httpurl);


                    //my code end
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        //boolean x = json.toLowerCase().contains(email.toLowerCase());
                        //if(x) {
                        sb.append(json + "\n");
                        //}
                    }
                    return abc;
                    //return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute(faculty_name_holder,urlWebService);
    }
    private void loadIntoView(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);
        data = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            //data[i] = obj.getString("subject") + " " + obj.getInt("attendance") + " " + obj.getInt("total_attendance"); //+ " "+ (float)obj.getInt("attendance")/obj.getInt("total_attendance");
           /* int x =  obj.getInt("attendance")/obj.getInt("total_attendance");
            float p = Float.parseFloat(x);
            stocks[i] = " " + p;*/
            faculty_subject = obj.getString("FacultySubject");

            show_facuty_name.setText(faculty_name_holder);
            show_subject.setText(faculty_subject);
             // subject_db = faculty_subject;

        }
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
        //listView.setAdapter(arrayAdapter);
        // tx.setText(stocks[1]);

    }


    private void downloadJSON2(final String Subject_db,final String urlWebService2) {

        class DownloadJSON2 extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoView2(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    //my code

                    //HttpClient httpclient = new DefaultHttpClient();
                    //HttpPost httppost = new HttpPost("http://192.168.0.101/VirtualAttendanceTracker/G/AccessStudentDetailsG.php?email=" + email);
                    //httpclient.execute(httppost);*/


                    // hashMap.put("password",params[1]);

                    /*FinalResult =*/
                   hashMap.put("Subject",params[0]);
                    abc2 =httpParse.postRequest(hashMap, urlWebService2);


                    //return FinalResult;

                    //httpParse.postRequest(hashMap, httpurl);


                    //my code end
                    URL url2 = new URL(urlWebService2);
                    HttpURLConnection con = (HttpURLConnection) url2.openConnection();
                    StringBuilder sb2 = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json2;
                    while ((json2 = bufferedReader.readLine()) != null) {
                        //boolean x = json.toLowerCase().contains(email.toLowerCase());
                        //if(x) {
                        sb2.append(json2 + "\n");
                        //}
                    }
                    return abc2;
                    //return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON2 getJSON = new DownloadJSON2();
        getJSON.execute(Subject_db,url2);
    }
    private void loadIntoView2(String json1) throws JSONException {
        JSONArray jsonArray2 = new JSONArray(json1);
        /*data2*/listItems = new String[jsonArray2.length()];

        for (int i = 0; i < jsonArray2.length(); i++) {
            JSONObject obj = jsonArray2.getJSONObject(i);
            listItems[i] = obj.getString("Roll_Number") + "::" + obj.getString("First_Name");


        }
        checkedItems = new boolean[listItems.length];

    }


    public void insertIntoTest(final String trimmed_roll_n,final String subject1,final String urlwebservice3){

        class insertintotestClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(Module_faculty.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //progressDialog.dismiss();

                // Toast.makeText(Module_faculty.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("Roll_Number",params[0]);
                hashMap.put("Subject",params[1]);





                FinalResult = httpParse.postRequest(hashMap, urlwebservice3);

                return FinalResult;
            }
        }

        insertintotestClass insertg = new insertintotestClass();

       insertg.execute(trimmed_roll_n,subject1,urlwebservice3);
    }



}
