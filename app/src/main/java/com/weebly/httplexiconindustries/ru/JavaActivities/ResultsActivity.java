package com.weebly.httplexiconindustries.ru.JavaActivities;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.weebly.httplexiconindustries.ru.ContentParsing.BookResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


import ActivityPackages.R;

@SuppressWarnings("ALL")
public class ResultsActivity extends AppCompatActivity {
    private FloatingSearchView searchField;
    private TextView textOut;
    private String sanitizedSearch;
    private GridView resultsGrid;
    private String toYear, fromYear;
    private int toYearSelected = 0;
    private int fromYearSelected = 0;
    private LinkedList<BookResult> bookList = new LinkedList<BookResult>();
    private static final int ROW_ITEMS = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        searchField = (FloatingSearchView) findViewById(R.id.floating_search_view);
        searchField.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.to_spinner) {
                    System.out.println("to spinner selected");
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResultsActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                    mBuilder.setTitle("choose a ending date below");
                    final Spinner mSpinner = (Spinner) mView.findViewById(R.id.yearspinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ResultsActivity.this,
                            android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.test_list));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(adapter);
                    mSpinner.setSelection(toYearSelected);

                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            toYear = mSpinner.getSelectedItem().toString();

                            toYearSelected = (mSpinner.getSelectedItemPosition());
                            dialogInterface.dismiss();
                        }
                    });
                    mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            dialogInterface.dismiss();
                        }
                    });
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                }
                if (item.getItemId() == R.id.from_spinner) {
                    System.out.println("from spinner selected");
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResultsActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                    mBuilder.setTitle("choose a starting date below");
                    final Spinner mSpinner = (Spinner) mView.findViewById(R.id.yearspinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ResultsActivity.this,
                            android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.test_list));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(adapter);
                    mSpinner.setSelection(fromYearSelected);

                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            fromYear = mSpinner.getSelectedItem().toString();
                            fromYearSelected = (mSpinner.getSelectedItemPosition());
                            dialogInterface.dismiss();
                        }
                    });
                    mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            dialogInterface.dismiss();
                        }
                    });
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }


            }
        });

        resultsGrid = (GridView) findViewById(R.id.resultsGrid);
        addSearch();
        resultsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                Toast.makeText(getBaseContext(),
                        "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * creates arraylist that can be used to populate gridview
     * @param searchPhrase
     * @throws IOException
     */
    private void createBooks(String searchPhrase) throws IOException {
        //searchPhrase = "lord+of+the+flies";
        String searchURL = "https://radforduniversity.on.worldcat.org/search?"
                + "databaseList=283&queryString=";
        bookList = new LinkedList<BookResult>();
        Document doc;
        doc = Jsoup.connect(searchURL + searchPhrase).timeout(60000).get();
        Elements title = doc.getElementsByClass("record-title");
        int check = 0;
        for (Element titles : title) {
            String text = titles.ownText();
            BookResult temp = new BookResult(text);
            bookList.add(temp);
            check++;
        }
        Elements img = doc.getElementsByTag("img");
        int counter = 0;
        int removeDoop = 0;
        // Loop through img tags
        for (Element el : img) {
            if (el.attr("src").substring(0, 2).equals("//") && removeDoop%2 == 0) {
                String imgURL = snipImgSrc(el.attr("src").trim());
                bookList.get(counter).setImgURL(imgURL);
                counter++;
                removeDoop++;
            }
        }

    }
    private void addSearch(){
        this.searchField.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {}
            @Override
            public void onSearchAction(String query) {
                sanitizedSearch = sanitizeInput(query);
                System.out.println(query);
                search(sanitizedSearch);
            }
        });
    }
    /**
     * searches books from the radford library catalog.
     * uses a secondary thread running concurrently with the main activity thread.
     * improves search speed significantly.
     * @param input search-ready String input from user.
     */
    private void search(final String input) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createBooks(input);
                    ArrayList<String> bookTitles = new ArrayList<String>();
                    int index = 0;
                    for (BookResult book : bookList) {
                        bookTitles.add(book.getName());
                        System.out.println(book.getName());
                    }
                    ResultsActivity.this.resultsGrid.setAdapter(new GridAdapter(bookTitles));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
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

    /**
     * removes all none alphanumeric chars, and also replaces spaces with '+' char
     * @param input standard user input
     * @return search-ready string with only alphanumeric values and spaces replaced with '+"s
     */
    private String sanitizeInput(String input){
        String output = input.replaceAll("[^A-Za-z0-9 ]", "");;
        output = output.trim();
        output = output.replaceAll("\\s+", "+");
        return output;
    }

    /**
     * this inner class is used for populating the GridView in activity_results.xml
     */
    private static final class GridAdapter extends BaseAdapter {

        final ArrayList<String> mItems;
        final int mCount;
        /**
         * Default constructor
         * @param items to fill data to
         */
        private GridAdapter(final ArrayList<String> items) {

            mCount = items.size();
            mItems = new ArrayList<String>(mCount);

            // for small size of items it's ok to do it here, sync way
            for (String item : items) {
                // get separate string parts, divided by ,
                final String[] parts = item.split(",");

                // remove spaces from parts
                for (String part : parts) {
                    part.replace(" ", "");
                    mItems.add(part);
                }
            }
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(final int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            final TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setText(mItems.get(position));
            text.setTextSize(10);

            return view;
        }
    }
}
