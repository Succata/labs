package com.labs.ex;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHolder extends RecyclerView.ViewHolder {
	ImageView imageView;
	public RecyclerHolder(View itemView) {
		super(itemView);
		imageView = itemView.findViewById(R.id.image);
	}
}
