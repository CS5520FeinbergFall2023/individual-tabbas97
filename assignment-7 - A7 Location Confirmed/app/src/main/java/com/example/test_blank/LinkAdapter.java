package com.example.test_blank;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkHolder> {

    private final List<Link> links;
    private final Context context;

    public LinkAdapter(List<Link> links, Context context) {
        this.links = links;
        this.context = context;
    }

    @Override
    public LinkHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
//        android.view.View view = android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
//        return new LinkHolder(view);
        return new LinkHolder(android.view.LayoutInflater.from(context).inflate(R.layout.item_link, null));
    }

    @Override
    public void onBindViewHolder(LinkHolder holder, int position) {
        holder.name.setText(links.get(position).getName());
        holder.link.setText(links.get(position).getLink());
        holder.itemView.setOnClickListener(view -> {
            // Launch the link in the browser
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(links.get(position).getLink()));
            context.startActivity(intent);
        });
        // TODO: Add long click listener - launch a new dialog with two options: edit and delete
        // TODO: 1. Edit - launch the add link dialog activity with the link name and link prefilled
        // TODO: 2. Delete - remove the link from the list and notify the adapter
        holder.itemView.setOnLongClickListener(view -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Edit or Delete Link");
            // Use positive button for edit. Edit will access the corresponding index in the links list and update the name and link
            builder.setPositiveButton("Edit", (dialogInterface, i) -> {
                // new dialog with two edittexts and a button
                android.app.AlertDialog.Builder editBuilder = new android.app.AlertDialog.Builder(context);
                editBuilder.setTitle("Edit Link");
                editBuilder.setView(android.view.LayoutInflater.from(context).inflate(R.layout.activity_edit_link, null));

                android.widget.EditText nameEditText = null;
                try {
//                    nameEditText = view.findViewById(R.id.edit_link_name);
                } catch (Exception e) {
                    System.out.println("DEBUG::: EXCEPTION: " + e);
                }
                System.out.println("DEBUG::: NAME EDIT TEXT: " + nameEditText.getText().toString());

                // Print all of links to console
                System.out.println("LINKS: ");
                for (Link link : links) {
                    System.out.println("LINKS:" + link.getName() + " " + link.getLink());
                }

                android.widget.EditText linkEditText = new android.widget.EditText(context);

                editBuilder.setPositiveButton("Update", (dialogInterface1, i1) -> {

                    System.out.println("DEBUG::: EDIT POSITIVE WORKING");

                    String name = "nameEditText.getText().toString()";
                    String link = "linkEditText.getText().toString()";

                    System.out.println("DEBUG::: NAME: " + name);
                    System.out.println("DEBUG::: LINK: " + link);

                    if (name.isEmpty() || link.isEmpty()) {
                        android.widget.Toast.makeText(context, "Please enter a name and link", android.widget.Toast.LENGTH_SHORT).show();
                        System.out.println("DEBUG::: EMPTY NAME OR LINK");
//                        return;
                    }

                    if (!link.startsWith("http://") && !link.startsWith("https://")) {
                        link = "http://" + link;
                    }

                    if (!android.util.Patterns.WEB_URL.matcher(link).matches()) {
                        android.widget.Toast.makeText(context, "Please enter a valid link", android.widget.Toast.LENGTH_SHORT).show();
                        System.out.println("DEBUG::: EMPTY NAME OR LINK");
//                        return;
                    }

                    links.set(position, new Link(name, link));
                    notifyDataSetChanged();
                });
                editBuilder.setNegativeButton("Cancel", (dialogInterface1, i1) -> {
                    dialogInterface1.cancel();
                });
                editBuilder.show();
            });
            builder.setNegativeButton("Delete", (dialogInterface, i) -> {
                links.remove(position);
                notifyDataSetChanged();
            });
            builder.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }
}
