package com.aptech.asmanjas.virtualattendancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends AppCompatActivity {

    Button SandP,S_only;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        SandP = (Button)findViewById(R.id.sPlusp);
        S_only = (Button)findViewById(R.id.s_only);

        SandP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        S_only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LauncherActivity.this,gLoginScreenActivity.class);
                startActivity(intent2);
            }
        });



    }
}
