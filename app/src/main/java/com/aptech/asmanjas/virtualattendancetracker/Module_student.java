package com.aptech.asmanjas.virtualattendancetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.HashMap;

public class Module_student extends AppCompatActivity {

    TextView student_name,roll_number,theory_batch,lab_batch,attendance_percent_tx;
    String roll_number_holder;
    Button followMe;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String abc = "";
    final static String student_roll_number = "";
    float attendance_percent;

    public String[] data,datax;
    public int[] idata,idata2,idata1,data2,data1;
    int total_attendance_sum =0;
    int total_classes_sum = 0;
   // ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_student);
        theory_batch = (TextView)findViewById(R.id.Theory_Batch_module_student);
        roll_number = (TextView)findViewById(R.id.email_id_module_g);
        student_name = (TextView)findViewById(R.id.student_name_module_g);
        lab_batch = (TextView)findViewById(R.id.LAB_Batch_module_student);
        attendance_percent_tx = (TextView)findViewById(R.id.attendance_percent_module_g);
followMe = (Button)findViewById(R.id.follow_me_module_student);

        Intent intent = getIntent();
        roll_number_holder = intent.getStringExtra(login_student.user_roll_number);
        roll_number.setText(roll_number_holder);




        String url =  "http://192.168.0.102/VirtualAttendanceTracker/AccessStudentDetails.php?Roll_Number=";
        String url2 = "http://192.168.0.102/VirtualAttendanceTracker/AccessStudentDetailsForAttendancePercent.php?Roll_Number=";
        String url3 = "http://192.168.0.102/VirtualAttendanceTracker/AccessStudentDetailsForAttendancePercent.php?Roll_Number=";

        downloadJSON(roll_number_holder, url + roll_number_holder);
        downloadJSON1(roll_number_holder, url2 + roll_number_holder);
        followMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(Module_student.this,showAttendance.class);
                intent2.putExtra(student_roll_number,roll_number_holder);
                intent2.putExtra("array1",data1);
                intent2.putExtra("array2",data2);
                startActivity(intent2);
            }
        });


    }
    private void downloadJSON(final String roll_number_holder,final String urlWebService) {

        class DownloadJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
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
                    hashMap.put("roll_number",params[0]);
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
        getJSON.execute(roll_number_holder,urlWebService);
    }

    private void loadIntoListView(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);
        data = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            //data[i] = obj.getString("subject") + " " + obj.getInt("attendance") + " " + obj.getInt("total_attendance"); //+ " "+ (float)obj.getInt("attendance")/obj.getInt("total_attendance");
           /* int x =  obj.getInt("attendance")/obj.getInt("total_attendance");
            float p = Float.parseFloat(x);
            stocks[i] = " " + p;*/
           String lb_batch = obj.getString("Lab_Batch");


            String th_batch = obj.getString("Theory_Batch");
            String name = obj.getString("First_Name") + " " + obj.getString("Last_Name");
            student_name.setText(name);
            theory_batch.setText(th_batch);
            lab_batch.setText(lb_batch);

        }
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
        //listView.setAdapter(arrayAdapter);
        // tx.setText(stocks[1]);

    }



    //downlaod Json1

    private void downloadJSON1(final String roll_number_holder,final String url2) {

        class DownloadJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView2(s);
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
                    hashMap.put("roll_number",params[0]);
                    abc =httpParse.postRequest(hashMap, url2);


                    //return FinalResult;

                    //httpParse.postRequest(hashMap, httpurl);


                    //my code end
                    URL url = new URL(url2);
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
        getJSON.execute(roll_number_holder,url2);
    }
    private void loadIntoListView2(String json2) throws JSONException {
        JSONArray jsonArray = new JSONArray(json2);
        data1 = new int[jsonArray.length()];
        data2 = new int[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            data1[i] = obj.getInt("subject_attendace");
            data2[i]=obj.getInt("Total_classes");
            // + " " + obj.getInt("attendance") + " " + obj.getInt("total_attendance"); //+ " "+ (float)obj.getInt("attendance")/obj.getInt("total_attendance");
           /* int x =  obj.getInt("attendance")/obj.getInt("total_attendance");
            float p = Float.parseFloat(x);
            stocks[i] = " " + p;*/
        }
        for(int i=0;i<jsonArray.length();i++)
        {
            total_attendance_sum = total_attendance_sum + data1[i];
            total_classes_sum = total_classes_sum + data2[i];
        }

        attendance_percent = (float)((total_attendance_sum *100)/total_classes_sum);
        attendance_percent_tx.setText(String.valueOf(attendance_percent + " %"));


            /*String lb_batch = obj.getString("Lab_Batch");


            String th_batch = obj.getString("Theory_Batch");
            String name = obj.getString("First_Name") + " " + obj.getString("Last_Name");
            student_name.setText(name);
            theory_batch.setText(th_batch);
            lab_batch.setText(lb_batch);*/



      /*  for(int i=0;i<jsonArray.length();i++)
        {
            idata1[i] = Integer.parseInt(data1[i]);
            idata2[i] = Integer.parseInt(data2[i]);
        }
        for(int i=0;i<jsonArray.length();i++){

            total_attendance_sum = total_attendance_sum + idata1[i];
            total_classes_sum = total_classes_sum + idata2[i];
        }
        attendance_percent = (float)((total_attendance_sum/total_classes_sum)*100);*/
        //attendance_percent_tx.setText(String.valueOf(attendance_percent) + "  asmanjas Kalundia");
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
        //listView.setAdapter(arrayAdapter);
        // tx.setText(stocks[1]);


    }
   /* public int getdata1()
    {
        return 10;
    }
    public int getdata2(int j)
    {
        return data2[j];
    }*/
}
