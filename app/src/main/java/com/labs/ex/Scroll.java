package com.labs.ex;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
		recyclerView = getActivity().findViewById(R.id.recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL,
				false));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(recyclerAdapter);
	}
}