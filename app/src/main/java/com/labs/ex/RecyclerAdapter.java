package com.labs.ex;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

	@Override
	public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		return new RecyclerHolder(inflater.inflate(R.layout.holder, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerHolder holder, int position) {
		LoadImageAsync async = new LoadImageAsync(holder.imageView, position, Main.data.get(holder.getAdapterPosition()).imageUri);
//		holder.imageView.setImageURI(Uri.parse(Main.data.get(holder.getAdapterPosition()).imageUri));
		async.execute();
		holder.header.setText(Main.data.get(holder.getAdapterPosition()).header);
		holder.body.setText(Main.data.get(holder.getAdapterPosition()).body);
		Main.data.size();
	}

	@Override
	public int getItemCount() {
		return Main.data.size();
	}

	public void addPost(String uri, String header, String body) {
		Main.data.add(new Post(uri, header, body));
		notifyDataSetChanged();
	}
}
