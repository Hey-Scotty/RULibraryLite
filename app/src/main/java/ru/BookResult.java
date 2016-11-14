package ru;

/**
 * Created by TinieT on 11/13/2016.
 */

public class BookResult {
    private String name;
    private String imgURL;

    public BookResult(String _name){
        setName(_name);
        imgURL = "";
    }

    public String getName() {
        return name;
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
}
