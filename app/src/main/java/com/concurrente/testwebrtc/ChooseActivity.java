package com.concurrente.testwebrtc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends Activity {

    Button audioButton;
    Button videoButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        intent = new Intent(getApplicationContext(), ComunicationActivity.class);

        audioButton = (Button) findViewById(R.id.audioButton);
        videoButton = (Button) findViewById(R.id.videoButton);

        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("tipo", "audio");
                startActivity(intent);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("tipo", "video");
                startActivity(intent);
            }
        });
    }
}