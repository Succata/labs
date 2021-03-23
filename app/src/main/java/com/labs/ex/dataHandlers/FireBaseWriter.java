package com.labs.ex.dataHandlers;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.labs.ex.activities.Main;
import com.labs.ex.acyncTasks.LoadFromVK;
import com.labs.ex.beans.Post;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseWriter implements DataWriter {
	@Override
	public void read() {
		DatabaseReference database = FirebaseDatabase.getInstance().getReference("items");

		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (Main.data.size() > 0) Main.data.clear();
				for (DataSnapshot ds : dataSnapshot.getChildren()){
					Post post = ds.getValue(Post.class);
					post.link = ds.getKey();
					Main.data.add(post);

				}

				if (Main.data.size() == 0) {
					ArrayList<VKScope> arrayList = new ArrayList<>();
					arrayList.add(VKScope.FRIENDS);
					arrayList.add(VKScope.WALL);
					VK.login(Main.context, arrayList);
				}

				Main.scrollFragment.recyclerAdapter.notifyDataSetChanged();
			}

			@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
		};
		database.addValueEventListener(valueEventListener);
	}

	@Override
	public void write() {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference myRef = database.getReference("items");
		myRef.push().setValue(Main.data.get(Main.data.size()-1));
	}

	@Override
	public void writeAll() {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference myRef = database.getReference("items");
		for (Post p: Main.data)
			myRef.push().setValue(p);
	}

	@Override
	public void delete(int pos) {
		FirebaseDatabase.getInstance().getReference("items").child(Main.data.get(pos).link).removeValue();
	}

	@Override
	public void redact(int pos, String... args) {
		Map<String, Object> map = new HashMap<>();
		map.put("imageUri", args[0]);
		map.put("header", args[1]);
		map.put("body", args[2]);

		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference myRef = database.getReference("items").child(Main.data.get(pos).link);
		myRef.updateChildren(map, (databaseError, databaseReference) -> {});
	}

	@Override
	public void loadFromVK() {
		LoadFromVK asyncTask = new LoadFromVK(this);
		asyncTask.execute();
	}
}
