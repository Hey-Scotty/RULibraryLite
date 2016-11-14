package ru;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ResultsActivity extends AppCompatActivity {

    TextView textOut;
    EditText getInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //textOut = (TextView) findViewById(R.id.resultsField);
        getInput = (EditText) findViewById(R.id.searchField);

        Button search = (Button) findViewById(R.id.GoButton);
        search.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v){
                try {
                    String searchOutput = pullSearchInfo(getInput.getText().toString());
                    Editable output = new SpannableStringBuilder(searchOutput);
                    //textOut.setText(getInput.getText(output));
                    //textOut.setText(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void buildList(){

    }
    private void createBooks() throws IOException {
        LinkedList<BookResult> linkedlist = new LinkedList<BookResult>();

        String searchURL = "https://radforduniversity.on.worldcat.org/search?"
                + "databaseList=283&queryString=";
        String phrase = "lord+of+the+flies";
        Document doc;
        doc = Jsoup.connect(searchURL + phrase).get();
        Elements title = doc.getElementsByClass("record-title");
        for (Element titles : title) {
            String text = titles.ownText();
            BookResult temp = new BookResult(text);
        }
        Elements img = doc.getElementsByTag("img");
        int counter = 0;
        int removeDoop = 0;
        // Loop through img tags
        for (Element el : img) {
            if (el.attr("src").substring(0, 2).equals("//")) {
                //each image occured twice this is to prevent duplicate images being added
                if(removeDoop%2 == 0){
                    linkedlist.get(counter).setImgURL(el.attr("src"));
                    counter++;
                }
                removeDoop++;
            }

        }
    }
    private String sanitizeInput(String input){
        String output = input.replaceAll("[^A-Za-z0-9 ]", "");;
        output = output.trim();
        output = output.replaceAll("\\s+", "+");
        return output;
    }
    private String pullSearchInfo(String searchInput) throws IOException {
        String output = "";
        Scanner scan = new Scanner(searchInput).useDelimiter(" ");
        String searchURL = "https://radforduniversity.on.worldcat.org/search?" +
                "databaseList=283&queryString=";
        String phrase = "harry+potter";
        Document doc;
        doc = Jsoup.connect(searchURL+searchInput).get();
        //Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        Elements title = doc.getAllElements();
        for(Element titles: title){
            String otherInfo = titles.className();
            String text = titles.ownText();
            if(otherInfo.equals("record-title")){
                output += "\n" + text + "\n";
                System.out.println(otherInfo + " ... and the text is: " + text);
            }
        }
        return output;
    }
}
