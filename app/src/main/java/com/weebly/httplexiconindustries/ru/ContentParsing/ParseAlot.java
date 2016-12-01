package com.weebly.httplexiconindustries.ru.ContentParsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Scotty on 11/30/16.
 */

public class ParseAlot {

    public LinkedList<BookResult> getBookList() {
        return bookList;
    }

    private LinkedList<BookResult> bookList = new LinkedList<BookResult>();

    public LinkedList<String> getPages() {
        return pages;
    }

    private LinkedList<String> pages = new LinkedList<String>();

    public ParseAlot(String inputURL) throws IOException {
        getAlot(inputURL);
    }

    private void getAlot(String inputURL) throws IOException{
        Document doc;
        doc = Jsoup.connect(inputURL).timeout(20000).get();
        Elements pageList = doc.getElementsByClass("core-pagination-page");
        for (int i=0;i < pageList.size();i++) {
            System.out.println("Just started");
            pages.add(pageList.get(i).getElementsByAttribute("href").attr("href"));
        }
        iteratePages();
    }
    private void iteratePages() throws IOException {
        String mainURL = "https://radforduniversity.on.worldcat.org/";
        for (int i=0;i<1;i++) {
            Document doc;
            doc = Jsoup.connect(mainURL+ pages.get(i)).timeout(20000).get();
            addToList(doc);
        }
    }

    private void addToList(Document doc){
        Elements title = doc.getElementsByClass("record-title");

        for (int i=0;i<title.size();i++) {
            String text = title.get(i).ownText();
            BookResult temp = new BookResult(text);
            bookList.add(temp);
        }
        Elements img = doc.getElementsByTag("img");
        int counter = 0;
        int removeDoop = 0;
        // Loop through img tags
        /*for (Element el : img) {
            if (el.attr("src").substring(0, 2).equals("//") && removeDoop%2 == 0) {
                String imgURL = snipImgSrc(el.attr("src").trim());
                bookList.get(counter).setImgURL(imgURL);
                counter++;
                removeDoop++;
            }
        }
        */
    }
    private String snipImgSrc(String input){
        String output = input;
        for(int i = 0; i < input.length(); i++){
            if(i > 2){
                if(input.substring(i, i+3).equals("jpg")){
                    output = "https:" + input.substring(0, i+3);
                    break;
                }
            }
        }
        return output;
    }
}
