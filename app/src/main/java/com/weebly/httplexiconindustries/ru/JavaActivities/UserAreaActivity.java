package com.weebly.httplexiconindustries.ru.JavaActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ActivityPackages.R;

 /*
 Created by Sam Harrison on 11/7/2016 with help from the tutorial provided by Tonikami TV
  */

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        final TextView settingsLink = (TextView) findViewById(R.id.tvSettings);
        final TextView welcome = (TextView) findViewById(R.id.tvWelcome);

        settingsLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent settingsIntent = new Intent(UserAreaActivity.this, SettingsActivity.class);
                UserAreaActivity.this.startActivity(settingsIntent);
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        String msg = "Welcome, " + name + "!";
        welcome.setText(msg);
        tvUsername.setText(username);
    }
}
