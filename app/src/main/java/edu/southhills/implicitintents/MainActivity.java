package edu.southhills.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etGoogleSearch = findViewById(R.id.etSearch);
        etGoogleSearch.requestFocus();

        // listen for enter key to do a google search
        etGoogleSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == keyEvent.ACTION_UP &&
                    i == KeyEvent.KEYCODE_ENTER) {
                    googleButton(view);
                }
                return false;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // clear search form when app restarts
        EditText etSearchQuery = findViewById(R.id.etSearch);
        etSearchQuery.setText("");
    }

    // button handler to launch google maps using search form data
    public void mapButton(View v){
        EditText etSearchQuery = findViewById(R.id.etSearch);
        String place = etSearchQuery.getText().toString();

        gotoMaps(place);
    }

    // button handler to do a google search using search form data
    public void googleButton(View v){
        EditText etSearchQuery = findViewById(R.id.etSearch);
        String criteria = etSearchQuery.getText().toString();

        googleSearch(criteria);
    }

    // button handler to share this app with a friend
    public void shareButton(View v){
        Intent fbIntent = new Intent(this, FeedBackActivity.class);
        startActivity(fbIntent);
    }

    // method to implicitly launch google search
    public void googleSearch(String query){
        String pageUri = "http://www.google.com/search?q=" + query;
        Uri google = Uri.parse(pageUri);

        Intent googleIntent = new Intent(Intent.ACTION_VIEW, google);
        Intent chooser = Intent.createChooser(googleIntent, "Google Search");

        try{
            startActivity(chooser);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "Search Browser not Found", Toast.LENGTH_SHORT).show();
        }
    }

    // method to explicitly launch google maps
    public void gotoMaps(String place){
        Uri location = Uri.parse("geo:0,0?q=" + place);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }
}
