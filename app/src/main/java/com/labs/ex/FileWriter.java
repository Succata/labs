package com.labs.ex;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter implements DataWriter {

	private static final String fileName = "data.json";

	@Override
	public void read() {
		try {
			FileInputStream fin = Main.context.openFileInput(fileName);
			byte[] bytes = new byte[fin.available()];
			fin.read(bytes);
			String res = new String(bytes);
			JSONObject json = new JSONObject(res);
			JSONArray data = json.getJSONArray("data");

			for (int i = 0; i < data.length(); i++) {
				JSONObject b = data.getJSONObject(i);
				Main.data.add(new Post(b.getString("uri"),b.getString("header"),b.getString("text")));
			}

		}catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			for (Post p: Main.data) {
				JSONObject buff = new JSONObject();
				buff.put("uri", p.imageUri);
				buff.put("header", p.header);
				buff.put("text", p.body);
				jsonArray.put(buff);
			}
			jsonObject.put("data", jsonArray);

			FileOutputStream fos = Main.context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(jsonObject.toString().getBytes());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int pos) {

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			for (int i = 0; i < Main.data.size(); i++) {
				JSONObject buff = new JSONObject();
				buff.put("uri", Main.data.get(i).imageUri);
				buff.put("header", Main.data.get(i).header);
				buff.put("text", Main.data.get(i).body);
				if (pos != i)
					jsonArray.put(buff);
			}
			jsonObject.put("data", jsonArray);

			FileOutputStream fos = Main.context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(jsonObject.toString().getBytes());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
}
