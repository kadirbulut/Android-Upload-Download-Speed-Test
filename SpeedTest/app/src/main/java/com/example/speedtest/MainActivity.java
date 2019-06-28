package com.example.speedtest;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import at.grabner.circleprogress.CircleProgressView;

public class MainActivity extends AppCompatActivity{

    private TextView upInfo1,upInfo2,upInfo11,upInfo22;
    private CircleProgressView circleProgressView1,circleProgressView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getPermissions();
        downloadTest();
        uploadTest();
    }

    private void initViews() {
        upInfo1 = findViewById(R.id.up_info1);
        upInfo2 = findViewById(R.id.up_info2);
        upInfo11 = findViewById(R.id.up_info11);
        upInfo22 = findViewById(R.id.up_info22);

        circleProgressView1 = findViewById(R.id.circleView1);
        circleProgressView2 = findViewById(R.id.circleView2);
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
    }


    private void downloadTest() {
        new DownloadSpeedTestTask(upInfo1,upInfo11,circleProgressView1).execute();
    }

    private void uploadTest() {
        new UploadSpeedTestTask(upInfo2,upInfo22,circleProgressView2).execute();
    }

}
