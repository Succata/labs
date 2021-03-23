package com.labs.ex.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.labs.ex.dataHandlers.BaseWriter;
import com.labs.ex.dataHandlers.FireBaseWriter;
import com.labs.ex.R;
import com.labs.ex.activities.Main;

public class Login extends Fragment {

	EditText login;
	EditText password;
	Toast toast = null;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		login = getActivity().findViewById(R.id.editText_login);
		password = getActivity().findViewById(R.id.editText_password);
		toast = Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT);
	}

	public boolean signIn(View view) {
		Main._isFirebase = ((Switch) getActivity().findViewById(R.id.base_switcher)).isChecked();
		if ((login.getText().toString().equals("") || password.getText().toString().equals("")) && toast.getView().getWindowVisibility() != View.VISIBLE) {
			toast.setText(getString(R.string.smthAreNull));
			toast.show();
		} else if (login.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
			Main.fileWriter = (Main._isFirebase)?new FireBaseWriter():new BaseWriter();
//			Main.fileWriter = new FileWriter();
			return true;
		} else if (toast.getView().getWindowVisibility() != View.VISIBLE) {
			toast.setText(getString(R.string.loginAndPassword_exception));
			toast.show();
		}
		return false;
	}
}