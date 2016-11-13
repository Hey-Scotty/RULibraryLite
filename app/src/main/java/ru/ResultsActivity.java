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
        textOut = (TextView) findViewById(R.id.resultsField);
        getInput = (EditText) findViewById(R.id.searchField);

        Button search = (Button) findViewById(R.id.GoButton);
        search.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v){
                try {
                    String searchOutput = pullSearchInfo();
                    Editable output = new SpannableStringBuilder(searchOutput);
                    //textOut.setText(getInput.getText(output));
                    textOut.setText(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private String pullSearchInfo() throws IOException {
        String output = "";
        String searchURL = "https://radforduniversity.on.worldcat.org/search?" +
                "databaseList=283&queryString=";
        String phrase = "harry+potter";
        Document doc;
        doc = Jsoup.connect(searchURL+phrase).get();
        //Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        Elements title = doc.getAllElements();
        for(Element titles: title){
            String otherInfo = titles.className();
            String text = titles.ownText();
            if(otherInfo.equals("record-title")){
                output += text + "\n";
                System.out.println(otherInfo + " ... and the text is: " + text);
            }
        }
        return output;
    }
}
