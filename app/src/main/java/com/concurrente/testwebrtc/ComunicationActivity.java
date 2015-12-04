package com.concurrente.testwebrtc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

public class ComunicationActivity extends AppCompatActivity {

    private XWalkView xWalkWebView;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunication);

        Intent intent = getIntent();
        tipo = intent.getStringExtra("tipo");

        xWalkWebView=(XWalkView)findViewById(R.id.xwalkWebView);
        //xWalkWebView.load("http://192.168.0.12:9000", null);

        // this loads a file from the assets/ directory
        if(tipo.equals("video")) {
            xWalkWebView.load("file:///android_asset/video.html", null);
        }else{
            xWalkWebView.load("file:///android_asset/audio.html", null);
        }
        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkWebView != null) {
            xWalkWebView.pauseTimers();
            xWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkWebView != null) {
            xWalkWebView.resumeTimers();
            xWalkWebView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkWebView != null) {
            xWalkWebView.onDestroy();
        }
    }
}
