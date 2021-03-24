package com.labs.ex.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.labs.ex.R;
import com.labs.ex.activities.Main;
import com.labs.ex.acyncTasks.LoadImageAsync;
import com.labs.ex.beans.Post;

import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

	@Override
	public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		return new RecyclerHolder(inflater.inflate(R.layout.holder, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerHolder holder, int position) {
		holder.imageView.setImageDrawable(Main.context.getDrawable(R.drawable.iconmonstr_picture_8));
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
		Main.data.add(new Post(uri, header, body, (Math.random()-0.5f)*180+","+(Math.random()-0.5f)*360));
		notifyDataSetChanged();
	}
}
