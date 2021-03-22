package com.labs.ex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

	public static Context context;

	private static final Login loginFragment = new Login();
	private static final Scroll scrollFragment = new Scroll();
	private static final AddImage addImage = new AddImage();
	public static ArrayList<Post> data = new ArrayList<>();
	FileWriter fileWriter = new FileWriter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = getApplicationContext();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null)
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment, loginFragment).commit();
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
		}
		fileWriter.read();
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
			scrollFragment.recyclerAdapter.addPost(addImage.uri, addImage.header.getText().toString(), addImage.text.getText().toString());
			addImage.header.setText("");
			addImage.text.setText("");
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment, scrollFragment).commit();
			fileWriter.write();
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
	}
}