package com.weebly.httplexiconindustries.ru.JavaActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ActivityPackages.R;


public class SearchHomeActivity extends AppCompatActivity {
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home);

        searchButton = (Button) findViewById(R.id.bSearch);

        // Capture button clicks
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SearchHomeActivity.this,
                        ResultsActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
