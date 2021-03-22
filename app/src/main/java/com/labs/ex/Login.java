package com.labs.ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

	EditText login;
	EditText password;
	Toast toast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login = findViewById(R.id.editText_login);
		password = findViewById(R.id.editText_password);
		toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
	}

	public void signIn(View view) {
		if ((login.getText().toString().equals("") || password.getText().toString().equals("")) && toast.getView().getWindowVisibility() != View.VISIBLE) {
			toast.setText(getString(R.string.smthAreNull));
			toast.show();
		} else if (login.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
			startActivity(new Intent(getApplicationContext(), Main.class));
		} else if (toast.getView().getWindowVisibility() != View.VISIBLE) {
			toast.setText(getString(R.string.loginAndPassword_exception));
			toast.show();
		}
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		login.setText(savedInstanceState.getCharSequence("loginTextValue"));
		password.setText(savedInstanceState.getCharSequence("passwordTextValue"));
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putCharSequence("loginTextValue", login.getText());
		outState.putCharSequence("passwordTextValue", password.getText());
	}
}