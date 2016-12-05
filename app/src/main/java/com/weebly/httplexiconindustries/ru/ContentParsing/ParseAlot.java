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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private LinkedList<BookResult> bookList = new LinkedList<BookResult>();
    public LinkedList<String> getPages() {
        return pages;
    }
    private LinkedList<String> pages = new LinkedList<String>();
    private String inputURLAttr = "";
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * only way of accessing this class is through this method as they are all private
     * this class is static so to use this method say ParseAlot.parse(inputString); this behaves
     * like the Math.random() method but instead of returning a random number this class returns
     * a listOfBook for the users search results
     * @param inputURL the url corresponding to the user's search input
     * @throws IOException
     */
    public LinkedList<BookResult> parse(String inputURL) throws IOException{
        inputURLAttr = inputURL;
        getAlot();
        return bookList;
    }

    /**
     * This method adds the URLs of up to the first 5 pages of results pertaining to the
     * user's search

     * @throws IOException
     */
    private void getAlot() throws IOException{
        Document doc;
        doc = Jsoup.connect(inputURLAttr).timeout(20000).get();
        Elements pageList = doc.getElementsByClass("core-pagination-page");
        addToList(doc);
        for (int i=0;i < pageList.size();i++) {
            pages.add(pageList.get(i).getElementsByAttribute("href").attr("href"));
        }
        iteratePages();
    }

    /**
     * this method iterates through the other results pages adding their search results to the
     * booklist varaible
     *      * this method can also be modified to change the search result size from 10 to 50
     * in increments of 10
     * @throws IOException
     */
    private void iteratePages() throws IOException {
        String mainURL = "https://radforduniversity.on.worldcat.org/";
        //change the amount here to  to increase or decease the amount of results in increments of
        // of 10, the amount can range from *** 1 to 5 ***
        int amtOfPagesToSearch = 2;
        for (int i=1;i<amtOfPagesToSearch;i++) {
            Document doc;
            if(pages.size() > 1) {
                doc = Jsoup.connect(mainURL + pages.get(i)).timeout(20000).get();
                addToList(doc);
                System.out.println("full url of the values " + mainURL + pages.get(i));
                System.out.println("added " + (i + 1) + "webpage");
            }
        }
    }

    /**
     * adds the book title and img URL from the current result page to the bookList class attribute
     * @param doc current result page
     */
    private void addToList(Document doc){
        Elements title = doc.getElementsByClass("record-title");

        for (int i=0;i<title.size();i++) {
            String text = title.get(i).ownText();
            String bookURL = "https://radforduniversity.on.worldcat.org/"+title.get(i).attr("href");
            BookResult temp = new BookResult(text,bookURL);
            bookList.add(temp);
        }
        Elements img = doc.getElementsByTag("img");
        int counter = 0;
        int removeDoop = 0;
        int bookLocation = 10;
        // Loop through adding img tags to the main book list
        for (int i = 0; i < img.size(); i++){
            Element el = img.get(i);
            if (removeDoop % 2 == 0 && bookLocation > 0 && el.attr("src").length() > 2 ) {
                if (el.attr("src").substring(0, 17).equals("//coverart.oclc.o")) {
                    String imgURL = snipImgSrc(el.attr("src"));
                    if(bookList.size() > 10)
                        bookList.get(bookList.size() - bookLocation).setImgURL(imgURL);
                    else if(bookList.size() <= 10)
                        bookList.get(counter).setImgURL(imgURL);
                    counter++;
                    bookLocation --;

                }
            }
            removeDoop++;
        }
    }

    /**
     * cuts down the inputted img src so that it can be directly parsed later by the
     * Picasso Library in the results Activity
     * @param input string before it has been cut down to a standardized image URL
     * @return the standardized image URL
     */
    private String snipImgSrc(String input){
        String output = input;
        for(int i = 0; i < input.length(); i++){
            if(i > 2 && i < input.length() - 3){
                if(input.substring(i, i+3).equals("jpg")){
                    output = "https:" + input.substring(0, i+3);
                    break;
                }
            }
        }
        return output;
    }
}
