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
    private String title;
    private String author;
    private String description;
    public BookData(String URL) throws IOException {
        Document doc = Jsoup.connect(URL).get();
        title = getTitle(doc);
        author = getAuthor(doc);
        description = getDesc(doc);
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }
    
    private String getAuthor(Document doc){
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
    private String getTitle(Document doc){
        String outputTitle = "";
        Elements title = doc.getElementsByClass("title");

        Elements holder = title.get(0).getElementsByTag("span");
        outputTitle = removeTags(holder.toString());
        return outputTitle;
    }
    private String getDesc(Document doc) {
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


}
