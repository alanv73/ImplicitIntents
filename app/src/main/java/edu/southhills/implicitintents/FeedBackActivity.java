package edu.southhills.implicitintents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FeedBackActivity extends AppCompatActivity {

    public static final String EMAIL_FEEDBACK_SUBJECT = "Check out this AWESOME app I found!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        findViewById(R.id.etRecipient).requestFocus();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // return to the main activity when the app restarts
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

    // button handler to call the sendEmail method using form data
    public void sendButton(View v){
        EditText etFeedback = findViewById(R.id.etFeedback);
        String message = etFeedback.getText().toString();

        EditText etRecipients = findViewById(R.id.etRecipient);
        String[] recipients = etRecipients.getText().toString().split(",");

        sendEmail(recipients, EMAIL_FEEDBACK_SUBJECT, message);
    }

    // button handler to return to the main activity
    public void backButton(View v){
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

    // send email using implicit intent
    public void sendEmail(String[] toField, String subject, String message){
        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        mailIntent.setType("plain/text");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, toField);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, message);

        Intent chooser = Intent.createChooser(mailIntent, "Send Email");

        try{
            startActivity(chooser);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "eMail Client not Found", Toast.LENGTH_SHORT).show();
        }

    }
}
