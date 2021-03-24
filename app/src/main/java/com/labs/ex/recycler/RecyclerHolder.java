package com.labs.ex.recycler;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.labs.ex.R;
import com.labs.ex.activities.Main;
import com.labs.ex.activities.Maps;

public class RecyclerHolder extends RecyclerView.ViewHolder {
	ImageView imageView;
	ImageView map;
	TextView header;
	TextView body;
	public RecyclerHolder(View itemView) {
		super(itemView);
		imageView = itemView.findViewById(R.id.image);
		header = itemView.findViewById(R.id.header);
		body = itemView.findViewById(R.id.postBody);
		map = itemView.findViewById(R.id.mapButton);
		map.setOnClickListener(v -> {
			Intent intent = new Intent(Main.context, Maps.class);
			intent.putExtra("coordinates", Main.data.get(getAdapterPosition()).mapAddress);
			Main.context.startActivity(intent);
		});
	}
}
