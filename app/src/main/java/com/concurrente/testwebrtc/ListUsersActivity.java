package com.concurrente.testwebrtc;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListUsersActivity extends AppCompatActivity {

    private String currentUserId;
    private ArrayAdapter<String> namesArrayAdapter;
    private ArrayList<String> names;
    private ListView usersListView;
    private Button logoutButton;
    private ProgressDialog progressDialog;
    private BroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        setConversationsList();
        super.onResume();
    }

    //display clickable a list of all users
    private void setConversationsList() {
        names = new ArrayList<String>();
        String usersName[] = {"Alice", "Bob", "Curly", "David"};
        int szUsersName = usersName.length;
        if (true) {
            for (int i = 0; i < szUsersName; i++) {
                names.add(usersName[i]);
            }

            usersListView = (ListView) findViewById(R.id.usersListView);
            namesArrayAdapter =
                    new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.user_list_item, names);
            usersListView.setAdapter(namesArrayAdapter);

            usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                    openConversation(names, i);
                }
            });

        } else {
            Toast.makeText(getApplicationContext(),
                    "Error loading user list",
                    Toast.LENGTH_LONG).show();
        }
    }

    //open a conversation with one person
    public void openConversation(ArrayList<String> names, int pos) {
        if (true) { /*Validar que siga conectado y otras cosas*/
            Intent intent = new Intent(getApplicationContext(), ChooseActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error finding that user",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
