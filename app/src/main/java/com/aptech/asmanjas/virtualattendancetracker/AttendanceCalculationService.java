package com.aptech.asmanjas.virtualattendancetracker;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Asmanjas on 04-04-2018.
 */

public class AttendanceCalculationService extends Service {

    HttpParse httpParse = new HttpParse();
    String abc = "",abc2;
    HashMap<String,String> hashMap = new HashMap<>();
    String email_holder;
    int[] startTimeInteger,endTimeInteger;
    String first_class_time;
    String location_db,location_db_in_main;

    double longt,lattt,longitude_from_db,lattitude_from_db;
    String longs,lats;
    String[] todaysSubject,startTime,endTime;
    String FinalResult= "";
    // Location lo;
    SimpleDateFormat timeformat = new SimpleDateFormat("k:mm");
    Date ll = new Date();
    String time_current = timeformat.format(ll);

    //url for getting classroom location from database
    String url4ClassRoomLocation = "http://192.168.0.102/VirtualAttendanceTracker/G/AccessGPSCoordinatesG.php";

    //url for getting subject and time  from database

   String url4TodaysClasses =  "http://192.168.0.102/VirtualAttendanceTracker/G/AccessTimeTableG.php";

    //url for updating attendance

    String url4UpdatingAttendance = "http://192.168.0.102/VirtualAttendanceTracker/G/UpdateAttendanceG.php";

    String url1 = "http://192.168.0.102/VirtualAttendanceTracker/G/insertintodummy.php";



    private LocationListener listener;
    private LocationManager locationManager;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        email_holder = intent.getStringExtra("email_id1");
        first_class_time = intent.getStringExtra("first_class_time");
       String hour = first_class_time.substring(0,2);
       String minute = first_class_time.substring(3,5);
       int hr = Integer.parseInt(hour);
       int min = Integer.parseInt(minute);
        int first_class_time = hr*60 + min;
        String hr_time_current = time_current.substring(0,2);
        String min_time_current = time_current.substring(3,5);
        int hour_current = Integer.parseInt(hr_time_current);
        int min_current = Integer.parseInt(min_time_current);
        int current_time_in_minute_from_service = hour_current*60 + min;
        final String testSubject = "DSA";


        getClassroomLocationFromDatabase(email_holder,url4ClassRoomLocation);
        getTodaysClassesDetails(email_holder,dayOfTheWeek,url4TodaysClasses);


       /* Calendar c = Calendar.getInstance();
        String current_time = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
        int current_time_in_minute = (c.get(Calendar.HOUR)*60) + c.get(Calendar.MINUTE);*/

       while(current_time_in_minute_from_service>= first_class_time && current_time_in_minute_from_service<first_class_time+60)
       {
           listener = new LocationListener() {
               @Override
               public void onLocationChanged(Location location) {
                   /*Intent i = new Intent("location_update");
                   i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                   sendBroadcast(i);*/
                   longt = location.getLongitude();
                   lattt = location.getLatitude();
                   location_db_in_main = getClassroomLocationFromDatabase(email_holder,url4ClassRoomLocation);

            int index = location_db_in_main.indexOf(" ");

            longs= location_db_in_main.substring(0 ,index);
            lats= location_db_in_main.substring(index+1 , location_db_in_main.length());

            longitude_from_db = Double.parseDouble(longs);
            lattitude_from_db = Double.parseDouble(lats);
                   Location loc11 = new Location("");
                   loc11.setLatitude(longt);
                   loc11.setLongitude(lattt);

                   Location loc22 = new Location("");
                   loc22.setLatitude(longitude_from_db);
                   loc22.setLongitude(lattitude_from_db);

                   float distanceInMeters = loc11.distanceTo(loc22);
                   if(distanceInMeters<20.00){
                       updateAttendance(email_holder,testSubject,url4UpdatingAttendance);
                   }

                   /*longs = Double.toString(longt);
                   lats = Double.toString(lattt);
                   insertIntoDB(longs,lats,url1);*/



               }

               @Override
               public void onStatusChanged(String s, int i, Bundle bundle) {

               }

               @Override
               public void onProviderEnabled(String s) {

               }

               @Override
               public void onProviderDisabled(String s) {
                   Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(i);
               }


           };
           current_time_in_minute_from_service = current_time_in_minute_from_service + 25;
       }

       // updateAttendance(email_holder,subject,url4UpdatingAttendance)






        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);

        return START_STICKY;

    }


    private void insertIntoDB(final String longitude,final String lattitude,final String urlWebService)
    {
        class insertIntoDBclass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                /*try {
                    loadIntoView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("longitude",params[0]);
                    hashMap.put("lattitude",params[1]);
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
        insertIntoDBclass getJSON = new insertIntoDBclass();
        getJSON.execute(longitude,lattitude,urlWebService);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }



    public String getClassroomLocationFromDatabase(final String email_holder,final String url4ClassRoomLocation) {

        class getClassRoomLocationFromDatabaseClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String a) {
                super.onPostExecute(a);
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                try {
                    abc2 = getClassRoomLocation(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    abc =httpParse.postRequest(hashMap, url4ClassRoomLocation);

                    URL url = new URL(url4ClassRoomLocation);
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
        getClassRoomLocationFromDatabaseClass getJSON = new getClassRoomLocationFromDatabaseClass();
        getJSON.execute(email_holder,url4ClassRoomLocation);
        return abc2;
    }
    public String getClassRoomLocation(String json1) throws JSONException {

        JSONArray jsonArray = new JSONArray(json1);
        String ak[] = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            location_db = obj.getString("longitude") + " " +obj.getString("lattitude");


            /*String longitude_db = obj.getString("longitude");
            String lattitude_db = obj.getString("lattitude");

            longitude_from_db = Double.parseDouble(longitude_db);
            lattitude_from_db = Double.parseDouble(lattitude_db);*/


        }
        return  location_db;
    }




    public void getTodaysClassesDetails(final String email_holder,final String dayOfTheWeek,final String url4TodaysClasses) {

        class TodaysClassesDetailsClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String a) {
                super.onPostExecute(a);
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                try {
                    getTodaysClasses(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    hashMap.put("email",params[0]);
                    hashMap.put("day",params[1]);
                    abc =httpParse.postRequest(hashMap, url4TodaysClasses);

                    URL url = new URL(url4TodaysClasses);
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
        TodaysClassesDetailsClass getJSON = new TodaysClassesDetailsClass();
        getJSON.execute(email_holder,dayOfTheWeek,url4TodaysClasses);
    }
    public void getTodaysClasses(String json1) throws JSONException {
        JSONArray jsonArray = new JSONArray(json1);
        todaysSubject = new String[jsonArray.length()];
        startTime = new String[jsonArray.length()];
        startTimeInteger = new int[jsonArray.length()];
        endTimeInteger = new int[jsonArray.length()];
        endTime = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            todaysSubject[i] = obj.getString("Subject");

            startTime[i] = obj.getString("Time_Start");
            endTime[i]=obj.getString("Time_End");


        }
        for(int i=0;i<jsonArray.length();i++)
        {
            startTimeInteger[i] = (Integer.parseInt(startTime[i].substring(0,2))*60) + Integer.parseInt(startTime[i].substring(3,5));
            endTimeInteger[i] = (Integer.parseInt(endTime[i].substring(0,2))*60) + Integer.parseInt(endTime[i].substring(3,5));

        }
    }



    private void updateAttendance(final String email_holder,final String subject,final String url4UpdatingAttendance){

        class upDateAttendanceClass extends AsyncTask<String,Void,String> {

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


                hashMap.put("email",params[0]);
                hashMap.put("subject",params[1]);





                FinalResult = httpParse.postRequest(hashMap, url4UpdatingAttendance);

                return FinalResult;
            }
        }

        upDateAttendanceClass insertg = new upDateAttendanceClass();

        insertg.execute(email_holder,subject,url4UpdatingAttendance);

    }
}
