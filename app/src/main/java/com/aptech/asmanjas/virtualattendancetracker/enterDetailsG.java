package com.aptech.asmanjas.virtualattendancetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class enterDetailsG extends AppCompatActivity {


    Button btn_subject_names,btn_done;
    HashMap<String,String> hashMap = new HashMap<>();
    String email_holder = "";
    public final String email_holder_parse = "";
    String finalresult = "";
    HttpParse httpParse = new HttpParse();
    String url = "http://192.168.0.102/VirtualAttendanceTracker/G/insertLocation.php";
    //ProgressDialog progressDialog = new ProgressDialog();

    /*placePicker start here*/

    int PLACE_PICKER_REQUEST = 1;
    TextView tvPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details_g);
        tvPlace = (TextView)findViewById(R.id.tvPlace);
        btn_subject_names = (Button)findViewById(R.id.enter_subject_names_enterDetailsG);
        Intent q = getIntent();
        email_holder = q.getStringExtra("user_email");
        btn_done = (Button)findViewById(R.id.done_button_enter_details_g);
        //tvPlace.setText(email_holder);

    btn_subject_names.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent a= new Intent(enterDetailsG.this,subjectNameG.class);
            a.putExtra("email_holder",email_holder);
            startActivity(a);
        }
    });
btn_done.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent k = new Intent(enterDetailsG.this,gLoginScreenActivity.class);
        startActivity(k);
    }
});

    }


    public void goPlacePicker(View view){
        //this is the place to call the picker function
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(enterDetailsG.this),PLACE_PICKER_REQUEST);
        }catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode== PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(enterDetailsG.this,data);
                //tvPlace.setText(place.getLatLng());
                final LatLng location = place.getLatLng();
                double latitude = location.latitude;
                double longitude = location.longitude;
                String longitude_s;
                longitude_s = String.valueOf(longitude);
                String lattitude_s = String.valueOf(latitude);
                insertLocation(email_holder,longitude_s,lattitude_s,url);
                tvPlace.setText(latitude + ","  + longitude);
            }
        }

    }

    /*place picker end*/

    public void insertLocation(final String email_h,final String lo,final String lat,final String urlwebservice){
        class insertLocationClass extends AsyncTask<String,Void,String > {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(SignUpG.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //progressDialog.dismiss();

                Toast.makeText(enterDetailsG.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("email",params[0]);
                hashMap.put("longitude",params[1]);

                hashMap.put("lattitude",params[2]);

                finalresult = httpParse.postRequest(hashMap, urlwebservice);

                return finalresult;
            }
        }

        insertLocationClass insertLocation = new insertLocationClass();
        insertLocation.execute(email_h,lo,lat,urlwebservice);
    }
}