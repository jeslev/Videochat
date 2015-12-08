package com.concurrente.testwebrtc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import org.apache.commons.io.IOUtils;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

import java.io.IOException;

public class ComunicationActivity extends AppCompatActivity {

    private XWalkView xWalkWebView;
    private String tipo;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_comunication);

        Intent intent = getIntent();
        tipo = intent.getStringExtra("tipo");
        username = intent.getStringExtra("username");

        xWalkWebView=(XWalkView)findViewById(R.id.xwalkWebView);
        //xWalkWebView.load("http://192.168.0.12:9000", null);

        // this loads a file from the assets/ directory
        if(tipo.equals("video")) {
            try {
                String content = IOUtils.toString(getAssets().open("video.html")).replaceAll("%USER_NAME%", username);
                //xWalkWebView.load("file:///android_asset/video.html", null);
                xWalkWebView.load("file:///android_asset/video.html", content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                String content = IOUtils.toString(getAssets().open("audio.html")).replaceAll("%USER_NAME%", username);
                //xWalkWebView.load("file:///android_asset/audio.html", null);
                xWalkWebView.load("file:///android_asset/audio.html", content);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
