package com.weebly.httplexiconindustries.ru.JavaActivities;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.weebly.httplexiconindustries.ru.ContentParsing.BookData;
import com.weebly.httplexiconindustries.ru.ContentParsing.BookResult;
import com.weebly.httplexiconindustries.ru.ContentParsing.ParseAlot;
import com.weebly.httplexiconindustries.ru.HelperPackage.GridAdapter;
import java.io.IOException;
import java.util.LinkedList;
import ActivityPackages.R;

/**
 * Results Activity is used to Search the entirety of the Radford University McConnell Library
 * Database for books and other forms of media held at the universities library
 */
public class ResultsActivity extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private FloatingSearchView searchField;
    private String sanitizedSearch;
    private GridView resultsGrid;
    MenuItem litButton,yearToButton,yearFromButton;
    private String toYear, fromYear;
    private int toYearPosition = 0;
    private int fromYearPosition = 0;
    private int litTypePosition = 0;
    private final String litTypeVal[] = {"&format=Book","&subformat=Book::book_printbook",
            "&subformat=Book::book_digital", "&format=Jrnl","&format=Video", "&format=Music"};
    private LinkedList<BookResult> bookList = new LinkedList<BookResult>();
    private static final int ROW_ITEMS = 2;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF CLASS INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * required on intial startUp of this activity Object
     * @param savedInstanceState currently not used fully, can be used to set the activity to a
     *                           previous instance such as a previous search
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        yearFromButton = (MenuItem) findViewById(R.id.from_spinner);

        addSearchListeners();
        System.out.println(litButton);
        resultsGrid = (GridView) findViewById(R.id.resultsGrid);
        resultsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(),
                        "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
                System.out.println(bookList.get(position).getName());
                BookData bookData = new BookData();
                try {bookData = new BookData(bookList.get(position));}
                catch (IOException e) {e.printStackTrace();}
                Intent intent = new Intent(ResultsActivity.this, SelectedBookActivity.class);
                intent.putExtra("bookData",bookData);
                startActivity(intent);
            }
        });
    }



    /**
     * if the Literature Type button is pressed in the search refinement menu it
     * opens dialog menu with spinner choices of literature type for user
     * @param item item determines which button is pressed
     *             in this case the item is the From Spinner
     */
    private void litButtonPressed(final MenuItem item){
        litButton = (MenuItem) findViewById(R.id.lit_type_spinner);
        if(item.getItemId() == R.id.lit_type_spinner) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResultsActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            mBuilder.setTitle("choose your type of media below");
            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.yearspinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ResultsActivity.this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.lit_type_list));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setSelection(litTypePosition);
            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    litTypePosition = (mSpinner.getSelectedItemPosition());
                    if(mSpinner.getSelectedItemPosition() != 0)
                        item.setTitle(mSpinner.getSelectedItem().toString());
                    dialogInterface.dismiss();
                    if(mSpinner.getSelectedItemPosition() == 0)
                        item.setTitle("Literature Type");
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
    /**
     * if the From Year button is pressed in the search refinement menu it
     * opens dialog menu with spinner choices of years for user
     * @param item item determines which button is pressed
     *             in this case the item is the From Spinner
     */
    private void fromYearButtonPress(final MenuItem item){
        if (item.getItemId() == R.id.from_spinner) {
            System.out.println("from spinner selected");
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResultsActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            mBuilder.setTitle("choose a starting date below");
            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.yearspinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ResultsActivity.this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.year_string_list));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setSelection(fromYearPosition);
            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    fromYear = mSpinner.getSelectedItem().toString().trim();
                    fromYearPosition = (mSpinner.getSelectedItemPosition());
                    if(mSpinner.getSelectedItemPosition() != 0)
                        item.setTitle("From " + fromYear);
                    if(mSpinner.getSelectedItemPosition() == 0)
                        item.setTitle("Year From");
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
    /**
     * if the To Year button is pressed in the search refinement menu it
     * opens dialog menu with spinner choices of years for user
     * @param item item determines which button is pressed
     *             in this case the item is the To Spinner
     */
    private void toYearButtonPress(final MenuItem item){
        if (item.getItemId() == R.id.to_spinner) {
            final MenuItem toSpinButton = (MenuItem) findViewById(R.id.to_spinner);
            System.out.println("to spinner selected");
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResultsActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            mBuilder.setTitle("choose a ending date below");
            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.yearspinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ResultsActivity.this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.year_string_list));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setSelection(toYearPosition);

            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    toYear = mSpinner.getSelectedItem().toString().trim();
                    toYearPosition = (mSpinner.getSelectedItemPosition());
                    item.setChecked(true);
                    if(mSpinner.getSelectedItemPosition() != 0)
                        item.setTitle("To " + toYear);
                    if(mSpinner.getSelectedItemPosition() == 0)
                        item.setTitle("Year To");
                    //toSpinButton.setTitle("Year To: " + toYear);
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
    /**
     * creates arraylist that can be used to populate gridview
     * @param searchPhrase
     * @throws IOException
     */
    private void createBooks(String searchPhrase) throws IOException {
        String completeURL = "";
        String searchURL = "https://radforduniversity.on.worldcat.org/search?"
                + "databaseList=283&queryString=";
        completeURL = searchURL+ searchPhrase;
        System.out.println("to year position" + toYearPosition);
        System.out.println( "from year position" + fromYearPosition);
        if(fromYearPosition != 0 && toYearPosition != 0) {
                completeURL += "&sortKey=SEARCH_RELEVANCE&scope=wz:1916&year=" + fromYear +
                        ".." + toYear;
        }
        //adds the literature type chosen by user to the search query
        else if(litTypePosition != 0){
            completeURL += litTypeVal[litTypePosition - 1];
        }

        completeURL += "&page=1";
        System.out.println("URL Before sent to parse Object"+completeURL);
        ParseAlot parseObj = new ParseAlot();
        bookList = parseObj.parse(completeURL);

    }

    /**
     * adds search Listener to The searchField variable including the menu buttons on the right side
     */
    private void addSearchListeners(){
        searchField = (FloatingSearchView) findViewById(R.id.floating_search_view);
        searchField.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                toYearButtonPress(item);
                fromYearButtonPress(item);
                litButtonPressed(item);

            }
        });
        this.searchField.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {}
            @Override
            public void onSearchAction(String query) {
                sanitizedSearch = sanitizeInput(query);
                new DownloadFilesTask().execute(sanitizedSearch);
            }
        });
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
     * This inner class offloads the searching to a seperate thread from the main UI thread
     * see AsyncTask Documentation on the Android Studio Webpage from info on the parameters and
     * syntax of this inner class
     */
    private class DownloadFilesTask extends AsyncTask<String, Void ,  Void> {
        ProgressDialog dialog;

        /**
         * before the serchBegins this method is called, putting a loading dialog message over the
         * activity, this is used to inform the user when the search has began and is closed once
         * the search has finished in the onPostExecute method below
         */
        protected void onPreExecute() {
            dialog = ProgressDialog.show(ResultsActivity.this,"","Loading. Please wait...", true);
        }
        @Override
        /**
         * main bulkwork takes place here, calls the createBooks method which creates a BookResult
         * ArrayList which in-turn is used to instantiate the bookList Object
         */
        protected Void doInBackground(String... strings) {
            String input = strings[0];
            try {

                createBooks(input);
            }
            catch (IOException e) {e.printStackTrace();}
            return null;
        }

        /**
         * once the bookList has been instantiated this method is executed, calling the gridAdapter
         * class to create the grid representation of the book list in this Results Activity
         * @param result
         */
        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            GridAdapter gridObj = new GridAdapter(ResultsActivity.this,bookList);
            ResultsActivity.this.resultsGrid.setAdapter(gridObj);


        }

    }

}
