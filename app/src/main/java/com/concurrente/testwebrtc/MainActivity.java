package com.concurrente.testwebrtc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //private Button signUpButton;
    private Button loginButton;
    private EditText usernameField;
    //private EditText passwordField;
    private String username;
    //private String password;
    private Intent intent;
    //private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //intent = new Intent(getApplicationContext(), ListUsersActivity.class);
        intent = new Intent(getApplicationContext(), ChooseActivity.class);


        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        //signUpButton = (Button) findViewById(R.id.signupButton);
        usernameField = (EditText) findViewById(R.id.loginUsername);
        //passwordField = (EditText) findViewById(R.id.loginPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                //password = passwordField.getText().toString();
                //if (username.equals(password)){
                    intent.putExtra("username", username);
                    startActivity(intent);
                //}
                //else{
                //    Toast.makeText(getApplicationContext(),
                //            "Usuario y/o Contraseña incorrectos!",
                //            Toast.LENGTH_LONG).show();
                //}
            }
        });
        /*
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
            }
        });
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
