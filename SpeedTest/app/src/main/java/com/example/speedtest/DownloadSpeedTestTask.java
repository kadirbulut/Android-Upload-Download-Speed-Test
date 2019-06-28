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

public class DownloadSpeedTestTask extends AsyncTask<Void, Void, String> implements OnTestCompleted {

    private TextView upInfo,upInfo2;
    private CircleProgressView circleProgressView;

    public DownloadSpeedTestTask(TextView upInfo1, TextView upInfo11, CircleProgressView circleProgressView1) {
        upInfo=upInfo1;
        upInfo2=upInfo11;
        upInfo.setText("Download Speed Result:");
        circleProgressView=circleProgressView1;
    }

    @Override
    protected String doInBackground(Void... params) {

        SpeedTestSocket speedTestSocket = new SpeedTestSocket();

        // add a listener to wait for speedtest completion and progress
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(SpeedTestReport report) {
                // called when download/upload is finished
                Log.v("121044005download", "[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
                Log.v("121044005download", "[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());
                updateInfo(report.getTransferRateBit());
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                // called when a download/upload error occur
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                // called to notify download/upload progress
                Log.v("121044005download", "[PROGRESS] progress : " + percent + "%");
                Log.v("121044005download", "[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
                Log.v("121044005download", "[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());
                updateInfo(report.getTransferRateBit());
            }
        });

        speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/100M.iso",1000);

        return null;
    }

    @Override
    public void updateInfo(BigDecimal rate) {
        upInfo.setText("Download Speed Result:"+ String.valueOf(rate)+"bits");
        float res = rate.floatValue();
        res = res * 0.00000095367f;
        upInfo2.setText(String.valueOf(res)+" Megabits");
        circleProgressView.setValue(Float.parseFloat(String.valueOf(res)));
    }
}