package com.weebly.httplexiconindustries.ru.ContentParsing;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by TinieT on 11/13/2016.
 */

public class BookResult {
    private String name;
    private String imgURL;
    private Drawable coverArt;
    public BookResult(String _name){
        setName(_name);
        imgURL = "";
        coverArt = null;
    }

    public String getName() {
        return name;
    }

    private static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the imgURL
     */
    public String getImgURL() {
        return imgURL;
    }

    /**
     * @param imgURL the imgURL to set
     */
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    public String toString(){
        String output = "";
        output += this.name + "\n";
        output += this.imgURL;
        return output;
    }

    public Drawable getCoverArt() {
        return coverArt;
    }

    public void setCoverArt() {
        this.coverArt = LoadImageFromWebOperations(this.imgURL);
    }
}
