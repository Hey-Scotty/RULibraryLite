package com.weebly.httplexiconindustries.ru.ContentParsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by TinieT on 11/22/2016.
 */

public class BookData {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private String title;
    private String author;
    private String description;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public BookData(String URL) throws IOException {
        Document doc = Jsoup.connect(URL).get();
        title = pullTitleData(doc);
        author = pullAuthorData(doc);
        description = pullAuthorData(doc);
    }

    /**
     * Gets the author's name for this specific book once a book has been selected to be viewed
     * by the user
     * @param doc html document that has the authors name
     * @return the author or authors of this specific book
     */
    private String pullAuthorData(Document doc){
        String outputAuthor = "";
        Elements author = doc.getElementsByClass("by");

        Elements holder = author.get(0).getElementsByTag("a");

        if(holder.size() == 1){
            outputAuthor = removeTags(holder.toString());
        }
        else if (holder.size() > 1){
            for(int i = 0; i < holder.size(); i++){
                outputAuthor += removeTags(holder.get(i).toString()) +"\n";
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
        String outputDesc = "";
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
     * get this description class Attribute
     * @return description: this description Attribute
     */
    public String getDescription() {
        return description;
    }


}
