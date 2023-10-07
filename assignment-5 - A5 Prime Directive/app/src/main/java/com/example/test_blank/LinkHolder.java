package com.example.test_blank;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinkHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView link;

    public LinkHolder(@NonNull android.view.View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.name);
        this.link = itemView.findViewById(R.id.link);
    }
}
