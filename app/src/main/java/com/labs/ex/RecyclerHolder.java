package com.labs.ex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHolder extends RecyclerView.ViewHolder {
	ImageView imageView;
	TextView header;
	TextView body;
	public RecyclerHolder(View itemView) {
		super(itemView);
		imageView = itemView.findViewById(R.id.image);
		header = itemView.findViewById(R.id.header);
		body = itemView.findViewById(R.id.postBody);
	}
}
