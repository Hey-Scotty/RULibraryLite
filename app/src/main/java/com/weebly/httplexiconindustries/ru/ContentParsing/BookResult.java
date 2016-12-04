package com.weebly.httplexiconindustries.ru.ContentParsing;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by TinieT on 11/13/2016.
 */

public class BookResult {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private String name;
    private String imgURL;
    private String bookURL;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Class Constructor instantiates the book chosen by user
     * @param _name
     */
    public BookResult(String _name, String _bookURL) {
        name = _name;
        bookURL = _bookURL;
        imgURL = "";

    }

    /**
     * @param imgURL the imgURL to set
     */
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    /**
     * @param bookURL the bookURL to set
     */
    public void setBookURL(String bookURL){this.bookURL = bookURL;}
    /**
     * gets the name attribute in this class
     * @return ame
     */
    public String getName() {
        return name;
    }

    /**
     * gets the imgURL Attribute
     * @return imgURL
     */
    public String getImgURL() {return imgURL;}

    /**
     * gets the imgURL Attribute
     * @return imgURL
     */
    public String getBookURL(){ return bookURL; }
    /**
     * this class returns a String representation of this class's attributes
     * @return output: a String representation of this class's attributes
     */
    public String toString(){
        String output = "";
        output += this.name + "\n";
        output += this.imgURL;
        return output;
    }

}
