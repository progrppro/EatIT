package com.bhukkad.eatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RiderActivity extends AppCompatActivity {

    private static final int time = 1500 ;
    private static long backpressed ;
    private Intent intent;
    private String user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        intent = getIntent() ;
        user = intent.getStringExtra("user") ;
    }
    @Override
    public void onBackPressed() {
        if(time + backpressed > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else {
            Toast.makeText(RiderActivity.this,"Press back again to exit",Toast.LENGTH_SHORT).show();
        }
        backpressed = System.currentTimeMillis() ;
    }
}
