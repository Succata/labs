package com.labs.ex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
		if ((login.getText().toString().equals("") || password.getText().toString().equals("")) && toast.getView().getWindowVisibility() != View.VISIBLE) {
			toast.setText(getString(R.string.smthAreNull));
			toast.show();
		} else if (login.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
			return true;
		} else if (toast.getView().getWindowVisibility() != View.VISIBLE) {
			toast.setText(getString(R.string.loginAndPassword_exception));
			toast.show();
		}
		return false;
	}
}