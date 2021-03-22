package com.labs.ex;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddImage extends Fragment {

	public String uri;
	public TextView header;
	public TextView text;
	ImageButton imageButton;
	Button button;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_add_image, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageButton = getActivity().findViewById(R.id.buttonImage);
		button = getActivity().findViewById(R.id.buttonAdd);
		header = getActivity().findViewById(R.id.add_header);
		text = getActivity().findViewById(R.id.add_text);
		uri = null;
	}

	public void setImage(Uri data) {
		uri = data.toString();
		imageButton.setImageURI(data);
	}
}