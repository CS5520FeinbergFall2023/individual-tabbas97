package com.example.test_blank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class AddLinkDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link_dialog);

        // If the user clicks outside the dialog, close the dialog
        this.setFinishOnTouchOutside(true);

        // Add action listener to Add Link button
        Button addLinkButton = findViewById(R.id.add_link_button);
        addLinkButton.setOnClickListener(view -> {

            // Retrieve the values for the name and link
            String name = ((android.widget.EditText) findViewById(R.id.link_name)).getText().toString();
            String link = ((android.widget.EditText) findViewById(R.id.link_url)).getText().toString();

            // Check if values are empty
            if (name.isEmpty() || link.isEmpty()) {
                android.widget.Toast.makeText(this, "Please enter a name and link", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate the link
            if (!link.startsWith("http://") && !link.startsWith("https://")) {
                link = "http://" + link;
            }

            // Check if link is valid
            if (!android.util.Patterns.WEB_URL.matcher(link).matches()) {
                android.widget.Toast.makeText(this, "Please enter a valid link", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }

            Link linkObject = new Link(name, link);

            // Add linkobject to the list in LinkCollector
            LinkCollector.linksList.add(linkObject);

            // Notify the adapter that the data has changed
            LinkCollector.linkRecyclerView.getAdapter().notifyDataSetChanged();

            // Set the linkAdded flag to true
            LinkCollector.linkAdded = true;

            // Close the dialog
            finish();
        });

        // Add action listener to Cancel button - just close
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }
}