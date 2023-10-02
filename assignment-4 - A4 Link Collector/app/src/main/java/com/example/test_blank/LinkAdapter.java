package com.example.test_blank;


import android.content.Context;
import android.view.View;

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
//        holder.itemView.setOnLongClickListener();
//        holder.itemView.setOnDragListener((view, dragEvent) -> {
//            ;
//        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }
}
