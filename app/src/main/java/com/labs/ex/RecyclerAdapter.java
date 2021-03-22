package com.labs.ex;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

	@Override
	public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		Log.d("tag", "-----created");
		return new RecyclerHolder(inflater.inflate(R.layout.holder, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerHolder holder, int position) {
		holder.imageView.setImageURI(Uri.parse(Main.data.get(holder.getAdapterPosition()).imageUri));
		Main.data.size();
		Log.d("tag", "-----bind");
	}

	@Override
	public int getItemCount() {
		return Main.data.size();
	}

	public void addPost(String uri) {
		Main.data.add(new Post(uri));
		notifyDataSetChanged();
	}
}
