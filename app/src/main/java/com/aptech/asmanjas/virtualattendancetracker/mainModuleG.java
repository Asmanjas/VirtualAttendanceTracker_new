package com.aptech.asmanjas.virtualattendancetracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
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
import java.util.Date;
import 	java.text.SimpleDateFormat;

public class mainModuleG extends AppCompatActivity {

    String email_holder;
    String abc="",subject ="DSA";
    String student_name;
    String firstClassTime,time,lastClassTime;

    HttpParse httpParse = new HttpParse();
    ArrayList<Integer> mUserItems = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();
    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    TextView student_name_tx,student_email_tx,Attendance_percent_tx;
    float attendance_percent;

    public String[] data,datax;
    public int[] idata,idata2,idata1,data2,data1;
    int total_attendance_sum =0;
    int total_classes_sum = 0;


    //getting the current day
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);


    //url for starting the service at specific time

    String urlForStartTime  = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessStartTimeOfService.php";

   String urlForEndTime = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessEndTimeForService.php";

    String url1 = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessStudentDetailsG.php";

    String url2 = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessStudentDetailsGG.php";
    String url3 = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessStudentDetailsForAttendancePercentG.php";




    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_module_g);
        student_name_tx = (TextView)findViewById(R.id.student_name_module_g);
        student_email_tx = (TextView)findViewById(R.id.email_id_module_g);
        Attendance_percent_tx = (TextView)findViewById(R.id.attendance_percent_module_g);

        Intent intent2 = getIntent();
        email_holder = intent2.getStringExtra(gLoginScreenActivity.user_email);
        student_email_tx.setText(email_holder);



        //startService(x);
        //startService(x);






        mOrder = (Button) findViewById(R.id.follow_me_module_g);

        accessStudentDetailsforDisplay(email_holder,subject,url1);
        getStudentDetailsforDialogBox(email_holder,url2);
        studentattendancepercent(email_holder,url3);
        getStartTimeForService(email_holder,dayOfTheWeek,urlForStartTime);
        getEndTimeForService(email_holder,dayOfTheWeek,urlForEndTime);





        //student_name_tx.setText(firstClassTime);





        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mainModuleG.this);
                mBuilder.setTitle(R.string.dialog_title);
                boolean[] checkItems;
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserItems.contains(position)) {
                                mUserItems.add(position);
                            } else if (mUserItems.contains(position)) {
                                mUserItems.remove(position);

                            }
                        }

                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {



                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (i = 0; i < checkedItems.length; i++) {
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
    }
        //listItems = //;getResources().getStringArray(R.array.student_name);

    private void getStudentDetailsforDialogBox(final String email_h,final String urlWebService) {

        class getStudentDetailsClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    abc =httpParse.postRequest(hashMap, urlWebService);

                    URL url = new URL(urlWebService);
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
        getStudentDetailsClass getJSON = new getStudentDetailsClass();
        getJSON.execute(email_h,urlWebService);
    }
    private void loadIntoView(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);
        listItems = new String[jsonArray.length()];
        checkedItems = new boolean[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            listItems[i] = obj.getString("subject") + "::  " + obj.getInt("attendance") + "  out of  " + obj.getInt("total_attendance");


        }



    }








   public void getStartTimeForService(final String email_h,final String day,final String urlForStartTime) {

        class accessStartTimeForServiceclass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String a) {
                super.onPostExecute(a);
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                try {
                    getStartTime(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    hashMap.put("day",params[1]);
                    abc =httpParse.postRequest(hashMap, urlForStartTime);

                    URL url = new URL(urlForStartTime);
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
        accessStartTimeForServiceclass getJSON = new accessStartTimeForServiceclass();
        getJSON.execute(email_h,day,urlForStartTime);
    }
    public void getStartTime(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);
        //String ak[] = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);


            firstClassTime = obj.getString("min(Time_Start)");
            //student_name_tx.setText(firstClassTime);
            //student_name_tx.setText(firstClassTime);
            //for calling the start service

            String hour,minute;
            int hr,min;
            hour = firstClassTime.substring(0,2);
            minute = firstClassTime.substring(3,5);
            hr = Integer.parseInt(hour);
            min = Integer.parseInt(minute);
            int k = hr*60 + min;

            //getting the class start time
           // student_name_tx.setText(String.valueOf(hr) + " " + String.valueOf(min));
            //student_name_tx.setText(String.valueOf(k));
            Intent x = new Intent(getApplicationContext(),AttendanceCalculationService.class);
            x.putExtra("email_id1",email_holder);
            x.putExtra("first_class_time",firstClassTime);
         startService(x);

            //student_name_tx.setText(firstClassTime);
            /*Calendar calendarx = Calendar.getInstance();
            calendarx.set(Calendar.HOUR_OF_DAY,hr );
            calendarx.set(Calendar.MINUTE, min);
            calendarx.set(Calendar.SECOND, 0);
            PendingIntent pi = PendingIntent.getService(this, 0,
                    new Intent(this, AttendanceCalculationService.class),PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendarx.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pi);*/

        }



    }



    private void accessStudentDetailsforDisplay(final String email_h,final String subject,final String urlwebservice) {

        class accessStudentDetailsforDisplayClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    setNameOnApp(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    hashMap.put("subject",params[1]);
                    abc =httpParse.postRequest(hashMap, urlwebservice);

                    URL url = new URL(urlwebservice);
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
        accessStudentDetailsforDisplayClass getJSON = new accessStudentDetailsforDisplayClass();
        getJSON.execute(email_h,subject,urlwebservice);
    }
    private void setNameOnApp(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);
        String ak[] = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);


            student_name = obj.getString("first_name") + " " + obj.getString("last_name");
           student_name_tx.setText(student_name);

        }



    }



    private void studentattendancepercent(final String email_h,final String urlwebservice3) {

        class studentAttendancePercentClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    attendance_percent_display(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    abc =httpParse.postRequest(hashMap, urlwebservice3);


                    URL url = new URL(urlwebservice3);
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
        studentAttendancePercentClass getJSON = new studentAttendancePercentClass();
        getJSON.execute(email_h,urlwebservice3);
    }
    private void attendance_percent_display(String json2) throws JSONException {
        JSONArray jsonArray = new JSONArray(json2);
        data1 = new int[jsonArray.length()];
        data2 = new int[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            data1[i] = obj.getInt("attendance");
            data2[i]=obj.getInt("total_attendance");

        }
        for(int i=0;i<jsonArray.length();i++)
        {
            total_attendance_sum = total_attendance_sum + data1[i];
            total_classes_sum = total_classes_sum + data2[i];
        }

        attendance_percent = (float)((total_attendance_sum *100)/total_classes_sum);
        Attendance_percent_tx.setText(String.valueOf(attendance_percent + " %"));





    }








    private void getEndTimeForService(final String email_h,final String day,final String urlForEndTime) {

        class accessEndTimeForServiceClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String a) {
                super.onPostExecute(a);
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                try {
                    getEndTime(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    hashMap.put("day",params[1]);
                    abc =httpParse.postRequest(hashMap, urlForEndTime);

                    URL url = new URL(urlForEndTime);
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
        accessEndTimeForServiceClass getJSON = new accessEndTimeForServiceClass();
        getJSON.execute(email_h,day,urlForEndTime);
    }
    private void getEndTime(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);
        String ak[] = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);


            lastClassTime = obj.getString("max(Time_End)");

            //for calling the start service
            int pp  = ((Integer.parseInt(lastClassTime.substring(0,2)))*60) + (Integer.parseInt(lastClassTime.substring(3,5)));
            //student_name_tx.setText(String.valueOf(pp));
           /* String hour,minute;
            int hr,min;
            hour = lastClassTime.substring(0,2);
            minute = lastClassTime.substring(3,5);
            hr = Integer.parseInt(hour);
            min = Integer.parseInt(minute);
            //student_name_tx.setText(String.valueOf(hr) + " " + String.valueOf(min));*/


            //student_name_tx.setText(firstClassTime);
          /* Calendar calendarx = Calendar.getInstance();
            calendarx.set(Calendar.HOUR_OF_DAY,16 );
            calendarx.set(Calendar.MINUTE, 23);
            calendarx.set(Calendar.SECOND, 0);*/
            /*PendingIntent pi = PendingIntent.getService(this, 0,
                    new Intent(this, AttendanceCalculationService.class),PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            am.cancel(pi);*/

            /*Intent intent = new Intent(this, AttendanceCalculationService.class);
            PendingIntent sender = PendingIntent.getBroadcast(this,
                    0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);*/

        }



    }


}
