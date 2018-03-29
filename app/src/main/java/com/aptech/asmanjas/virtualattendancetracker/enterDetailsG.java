package com.aptech.asmanjas.virtualattendancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

public class enterDetailsG extends AppCompatActivity {


    Button btn_subject_names,btn_entertimetable;
    String email_holder = "";
    public final String email_holder_parse = "";

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
        email_holder = q.getStringExtra(SignUpG.user_email);

    btn_subject_names.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent a= new Intent(enterDetailsG.this,subjectNameG.class);
            //a.putExtra(email_holder_parse,email_holder);
            startActivity(a);
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
                final double latitude = location.latitude;
                final double longitude = location.longitude;
                tvPlace.setText(latitude + ","  + longitude);
            }
        }

    }

    /*place picker end*/
}