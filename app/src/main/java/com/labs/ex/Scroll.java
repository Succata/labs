package com.labs.ex;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Scroll extends Fragment {

	RecyclerView recyclerView;
	RecyclerAdapter recyclerAdapter;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		recyclerAdapter = new RecyclerAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_scroll, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		new FileWriter().read();
		recyclerView = getActivity().findViewById(R.id.recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL,
				false));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(recyclerAdapter);

		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
				ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

			@Override
			public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
								  @NonNull RecyclerView.ViewHolder viewHolder1) {
				return false;
			}

			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
				if (i == ItemTouchHelper.LEFT){
					new FileWriter().delete(viewHolder.getAdapterPosition());
//					Main.data.remove(viewHolder.getAdapterPosition());
					recyclerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
				} else if (i == ItemTouchHelper.RIGHT) {
					Main._isRedact = true;
					Main._redactPosition = viewHolder.getAdapterPosition();
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, ((Main)getActivity()).addImage).commit();
				}
			}
		});
		itemTouchHelper.attachToRecyclerView(recyclerView);
	}
}