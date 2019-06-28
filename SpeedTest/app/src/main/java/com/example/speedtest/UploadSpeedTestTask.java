package com.example.speedtest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;

import at.grabner.circleprogress.CircleProgressView;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

public class UploadSpeedTestTask extends AsyncTask<Void, Void, Integer> implements OnTestCompleted  {

    private TextView upInfo,upInfo22;
    private CircleProgressView circleProgressView;

    public UploadSpeedTestTask(TextView upInfo2, TextView upInfo22, CircleProgressView circleProgressView2) {
        upInfo = upInfo2;
        this.upInfo22 = upInfo22;
        upInfo.setText("Upload Speed Result:");
        circleProgressView = circleProgressView2;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        SpeedTestSocket speedTestSocket = new SpeedTestSocket();

        // add a listener to wait for speedtest completion and progress
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(SpeedTestReport report) {
                // called when download/upload is finished
                Log.v("121044005upload", "[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
                Log.v("121044005upload", "[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());
                updateInfo(report.getTransferRateBit());
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                // called when a download/upload error occur
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                // called to notify download/upload progress
                Log.v("121044005upload", "[PROGRESS] progress : " + percent + "%");
                Log.v("121044005upload", "[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
                Log.v("121044005upload", "[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());
                updateInfo(report.getTransferRateBit());
            }
        });

        speedTestSocket.startUpload("http://ipv4.ikoula.testdebit.info/", 10000000);

        return null;
    }

    @Override
    public void updateInfo(BigDecimal rate) {
        upInfo.setText("Upload Speed Result:"+ String.valueOf(rate)+"bits");
        float res = rate.floatValue();
        res = res * 0.00000095367f;
        upInfo22.setText(String.valueOf(res)+" Megabits");
        circleProgressView.setValue(Float.parseFloat(String.valueOf(res)));
    }
}