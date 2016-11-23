package com.weebly.httplexiconindustries.ru.JavaActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.weebly.httplexiconindustries.ru.AccountAccess.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import ActivityPackages.R;
 /*
 Created by Sam Harrison on 11/7/2016 with help from the tutorial provided by Tonikami TV
  */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegister);
        final TextView continueLink = (TextView) findViewById(R.id.tvContinue);

        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        continueLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent continueIntent = new Intent(LoginActivity.this, ResultsActivity.class);
                LoginActivity.this.startActivity(continueIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String name = jsonResponse.getString("name");
                                String email = jsonResponse.getString("email");

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);

                                LoginActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Invalid Credentials.")
                                        .setNegativeButton("Try Again", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
