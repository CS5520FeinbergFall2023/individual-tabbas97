package com.example.test_blank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollector extends AppCompatActivity {

    static RecyclerView linkRecyclerView;
    static List<Link> linksList;

    static boolean linkAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        linksList = new ArrayList<>();

        linkRecyclerView = findViewById(R.id.links_recycler_view);

        linkRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        linkRecyclerView.setAdapter(new LinkAdapter(linksList, this));

        // Add action listener to the button
        findViewById(R.id.link_add_button).setOnClickListener(view -> {
            // Launch the add link dialog activity
            android.content.Intent intent = new android.content.Intent(LinkCollector.this, AddLinkDialog.class);
            startActivity(intent);

            if (linkAdded) {
                // Launch a snackbar to notify the user that the link has been added
                Snackbar.make(view, "Link added", Snackbar.LENGTH_SHORT).show();

                linkAdded = false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Convert the list to an arraylist of arraylist and save it in the bundle
        ArrayList<ArrayList<String>> links = new ArrayList<>();
        for (Link link : linksList) {
            ArrayList<String> linkInfo = new ArrayList<>();
            linkInfo.add(link.getName());
            linkInfo.add(link.getLink());
            links.add(linkInfo);
        }
        outState.putSerializable("links", links);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Retrieve the arraylist of arraylist from the bundle and convert it to a list of Link objects
        ArrayList<ArrayList<String>> links = (ArrayList<ArrayList<String>>) savedInstanceState.getSerializable("links");
        for (ArrayList<String> linkInfo : links) {
            linksList.add(new Link(linkInfo.get(0), linkInfo.get(1)));
        }
    }
}