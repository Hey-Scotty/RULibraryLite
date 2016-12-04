package com.weebly.httplexiconindustries.ru.JavaActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import ActivityPackages.R;

 /*
 Created by Sam Harrison on 11/7/2016 with help from the tutorial provided by Tonikami TV
  */

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final TextView ID = (TextView) findViewById(R.id.tvUserID);
        final TextView welcome = (TextView) findViewById(R.id.tvWelcome);

        Intent intent = getIntent();
        //String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String userID = intent.getStringExtra("userID");
        //String email = intent.getStringExtra("email");

        String msg = "Welcome, " + username + "!";
        ID.setText((userID));
        welcome.setText(msg);
    }
}
