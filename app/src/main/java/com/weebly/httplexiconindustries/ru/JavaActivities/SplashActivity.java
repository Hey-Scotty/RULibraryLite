package com.weebly.httplexiconindustries.ru.JavaActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ActivityPackages.R;

/**
 * Created by Scotty on 11/24/16.
 */

public class SplashActivity extends Activity{
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
