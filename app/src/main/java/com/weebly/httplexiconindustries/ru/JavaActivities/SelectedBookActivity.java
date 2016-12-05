package com.weebly.httplexiconindustries.ru.JavaActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cengalabs.flatui.FlatUI;
import com.squareup.picasso.Picasso;
import com.weebly.httplexiconindustries.ru.ContentParsing.BookData;

import ActivityPackages.R;

public class SelectedBookActivity extends AppCompatActivity {
    private BookData bookData = new BookData();
    private TextView title;
    private TextView author;
    private TextView publishYear;
    private TextView desc;
    private ImageView coverArt;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_book);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.DEEP);
        FlatUI.setDefaultTheme(R.array.ru_theme);
        Intent intent = getIntent();
        bookData = (BookData) intent.getSerializableExtra("bookData");
        title = (TextView) findViewById(R.id.title_field);
        author = (TextView) findViewById(R.id.author_field);
        publishYear = (TextView) findViewById(R.id.pub_year_field);
        desc = (TextView) findViewById(R.id.desc_field);
        coverArt = (ImageView) findViewById(R.id.cover_art);
        backButton = (Button) findViewById(R.id.back_button);
        if (bookData != null && bookData.getTitle() != null)
            title.setText(bookData.getTitle());
        if (bookData != null && bookData.getAuthor() != null)
            author.setText(bookData.getAuthor());
        if (bookData != null && bookData.getPublishYear() != null)
            publishYear.setText(bookData.getPublishYear());
        if (bookData != null && bookData.getDescription()!= null)
            desc.setText(bookData.getDescription());
        if (bookData != null && bookData.getImgURL() != null)
            Picasso.with(this).load(bookData.getImgURL()).into(coverArt);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

}
