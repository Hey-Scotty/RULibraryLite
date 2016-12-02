package com.weebly.httplexiconindustries.ru.JavaActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.weebly.httplexiconindustries.ru.AccountAccess.RegisterRequest;


import org.json.JSONException;
import org.json.JSONObject;

import ActivityPackages.R;

/*
Created by Sam Harrison on 11/7/2016 with help from the tutorial provided by Tonikami TV
 */

public class RegisterActivity extends AppCompatActivity {

    boolean verified;

    public RegisterActivity(){
        boolean verified = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);

        final Button bRegister = (Button) findViewById(R.id.bLogin);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String cPassword = etConfirmPassword.getText().toString();
                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (password.equals(cPassword)) {
                                if(etUsername.getText().toString().trim().equals("") || etName.getText().toString().trim().equals("") || etEmail.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals("")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("One or More Text Fields are Empty.")
                                            .setNegativeButton("Try Again", null)
                                            .create()
                                            .show();
                                }
                                else {
                                    if (success) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        RegisterActivity.this.startActivity(intent);
                                        setSuccess(true);

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage("Register Failed.")
                                                .setNegativeButton("Try Again", null)
                                                .create()
                                                .show();
                                    }
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Passwords Do Not Match.")
                                        .setNegativeButton("Try Again", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if(etUsername.getText().toString().trim().equals("") || etName.getText().toString().trim().equals("") || etEmail.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("One or More Text Fields are Empty.")
                            .setNegativeButton("Try Again", null)
                            .create()
                            .show();
                }
                else {
                    RegisterRequest registerRequest = new RegisterRequest(username, password, name, email, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
    }

    public void setSuccess(boolean b){
        verified = b;
    }

    public boolean getSuccess(){
        return verified;
    }
}
