package com.aptech.asmanjas.virtualattendancetracker;

import android.annotation.SuppressLint;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Asmanjas on 04-04-2018.
 */

public class AttendanceCalculationService extends Service {

    HttpParse httpParse = new HttpParse();
    String abc = "";
    HashMap<String,String> hashMap = new HashMap<>();
    String email_holder;

    double longt,lattt;
    String longs,lats;
    // Location lo;
    String url1 = "http://192.168.0.102/VirtualAttendanceTracker/G/insertintodummy.php";

    private LocationListener listener;
    private LocationManager locationManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        email_holder = intent.getStringExtra("email_id");

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);
                longt = location.getLongitude();
                lattt = location.getLatitude();

                longs = Double.toString(longt);
                lats = Double.toString(lattt);
                insertIntoDB(longs,lats,url1);
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
}
