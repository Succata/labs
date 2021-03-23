package com.labs.ex.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.labs.ex.fragments.AddImage;
import com.labs.ex.dataHandlers.DataWriter;
import com.labs.ex.fragments.Login;
import com.labs.ex.beans.Post;
import com.labs.ex.R;
import com.labs.ex.fragments.Scroll;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

	public static Activity context;

	private static final Login loginFragment = new Login();
	public static final Scroll scrollFragment = new Scroll();
	public static final AddImage addImage = new AddImage();
	public static ArrayList<Post> data = new ArrayList<>();
	public static DataWriter fileWriter;

	public static boolean _isRedact = false;
	public static boolean _isFirebase = false;
	public static int _redactPosition = -1;
	public static String vkToken = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null)
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment, loginFragment).commit();
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
		}
	}

	public void signIn(View view) {
		if (loginFragment.signIn(view))
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment, scrollFragment).commit();
	}

	public void addImage(View view) {
		startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), 0);
	}

	public void addPost(View view) {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addImage).commit();
	}
	public void completedAddPost(View view) {
		if (addImage.uri != null && !addImage.header.getText().equals("") && !addImage.text.getText().equals("")) {
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment, scrollFragment).commit();
			if (_isRedact)
				fileWriter.redact(_redactPosition, addImage.uri, addImage.header.getText().toString(), addImage.text.getText().toString());
			else {
				scrollFragment.recyclerAdapter.addPost(addImage.uri, addImage.header.getText().toString(), addImage.text.getText().toString());
				fileWriter.write();
			}
			addImage.header.setText("");
			addImage.text.setText("");
			_isRedact = false;
			_redactPosition = -1;
		} else Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0){
			if (resultCode == RESULT_OK) {
				addImage.setImage(data.getData());
			}
		}
		if (requestCode == 282) {
			VK.onActivityResult(requestCode, resultCode, data, new VKAuthCallback() {
				@Override
				public void onLogin(VKAccessToken vkAccessToken) {
					vkToken = vkAccessToken.getAccessToken();
					Log.d("log", "vkmsg");
					fileWriter.loadFromVK();
				}

				@Override public void onLoginFailed(int i) { }
			});
		}
	}
}