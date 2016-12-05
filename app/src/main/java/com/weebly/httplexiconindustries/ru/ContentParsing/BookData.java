package com.weebly.httplexiconindustries.ru.ContentParsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by TinieT on 11/22/2016.
 */

public class BookData implements Serializable{
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final long serialVersionUID = 1L;
    private String title = " ";
    private String author = " ";
    private String publishYear = " ";
    private String description = " ";
    private String imgURL = null;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public BookData(){

    }
    public BookData(BookResult book) throws IOException {
        Document doc = Jsoup.connect(book.getBookURL()).get();
        title = pullTitleData(doc);
        author = pullAuthorData(doc);
        publishYear = pullPublishYearData(doc);
        description = pullDescriptionData(doc);
        imgURL = book.getImgURL();
    }

    /**
     * Gets the author's name for this specific book once a book has been selected to be viewed
     * by the user
     * @param doc html document that has the authors name
     * @return the author or authors of this specific book
     */
    private String pullAuthorData(Document doc){
        String outputAuthor = "none";
        Elements author = doc.getElementsByClass("by");
        if(author.size() > 0) {
            Elements holder = author.get(0).getElementsByTag("a");

            if (holder.size() == 1) {
                outputAuthor = removeTags(holder.toString());
            } else if (holder.size() > 1) {
                outputAuthor = "";
                for (int i = 0; i < holder.size(); i++) {
                    outputAuthor += removeTags(holder.get(i).toString()) + ", ";
                }
            }
        }
        return outputAuthor;
    }
    /**
     * Gets the title of the for this specific book once a book has been selected to be viewed
     * by the user
     * @param doc doc html document that has the book name
     * @return the title of this specific book
     */
    private String pullTitleData(Document doc){
        String outputTitle = "";
        Elements title = doc.getElementsByClass("title");

        Elements holder = title.get(0).getElementsByTag("span");
        outputTitle = removeTags(holder.toString());
        return outputTitle;
    }
    /**
     * Gets the description of the for this specific book once a book has been selected to be viewed
     * by the user
     * @param doc: html document that has the description of the book
     * @return the description of this specific book
     */
    private String pullDescriptionData(Document doc) {
        String outputDesc = "No Description Found";
        Elements descData = doc.getElementsByClass("abstract");
        if (descData.size() > 0) {
            Element descText = descData.get(1);
            outputDesc = descText.getElementsByTag("li").toString();
            outputDesc = outputDesc.substring(4, outputDesc.length() - 5);
        } else {
            descData = doc.getElementsByClass("summary");
            if (descData.size() > 0) {
                Element descText = descData.get(1);
                outputDesc = descText.getElementsByTag("li").toString();
                outputDesc = outputDesc.substring(4, outputDesc.length() - 5);
            }
        }
        return outputDesc;
    }
    private String pullPublishYearData(Document doc){
        String output = "no publish date found";
        Elements El = doc.getElementsByClass("basicBib");
        Element holder = El.get(0);
        output = removeTags((holder).getElementsByTag("dd").get(1).toString()).trim();

        return output;
    }

    /**
     * removes tag braces **<></>** from the strings. used in the pullTitleData and pullAuthorData methods
     * @param withTags: the string before tags have been removed
     * @return removed: the string after tags have been removed
     */
    private String removeTags(String withTags){


        String removed = withTags;
        int firstBraceLoc = 0;
        boolean braceBeforeFound = false;
        for(int i = 0; i < withTags.length(); i++){
            if(withTags.charAt(i) == '>' && !braceBeforeFound){
                braceBeforeFound = true;
                firstBraceLoc = i + 1;
            }
            if(withTags.charAt(i) == '<' && braceBeforeFound){
                removed = removed.substring( firstBraceLoc , i);
                break;
            }
        }
        return removed;
    }

    /**
     * get this title class Attribute
     * @return title: this title Attribute
     */
    public String getTitle() {
        return title;
    }
    /**
     * get this author class Attribute
     * @return author: this author Attribute
     */
    public String getAuthor() {
        return author;
    }
    /**
     * get this publishYear class Attribute
     * @return publishYear: this publish year Attribute
     */
    public String getPublishYear() {
        return publishYear;
    }
    /**
     * get this description class Attribute
     * @return description: this description Attribute
     */
    public String getDescription() {
        return description;
    }
    /**
     * get this image URL class Attribute
     * @return imgURL: this Image URL Attribute
     */
    public String getImgURL() {return imgURL;}


}
