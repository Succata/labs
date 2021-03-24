package com.labs.ex.acyncTasks;

import android.os.AsyncTask;

import com.labs.ex.beans.Post;
import com.labs.ex.activities.Main;
import com.labs.ex.dataHandlers.DataWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class LoadFromVK extends AsyncTask<Void, Void, JSONObject> {
	DataWriter dataWriter;
	public LoadFromVK(DataWriter dataWriter) {
		this.dataWriter = dataWriter;
	}
	@Override
	protected JSONObject doInBackground(Void... integers) {
		JSONObject response = null;
		try {
			String METHOD = "newsfeed.getRecommended";
			String VERSION = "5.130";
			String MAX_PHOTOS = "1";
			String COUNT = "5";
			URL request = new URL("https://api.vk.com/method/" + METHOD + "?access_token=" + Main.vkToken + "&v=" + VERSION + "&max_photos=" + MAX_PHOTOS + "&count=" + COUNT);
			Scanner scanner = new Scanner(request.openStream());
			response = new JSONObject(scanner.useDelimiter("\\Z").next());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		try {
			JSONObject response = result.getJSONObject("response");
			JSONArray items = response.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				JSONObject obj = items.getJSONObject(i);
				String image = null;
				String text = obj.getString("text");
				try {
					JSONArray attachments = obj.getJSONArray("attachments");
					for (int j = 0; j < attachments.length(); j++) {
						if (attachments.getJSONObject(j).getString("type").equals("photo")) {
							JSONArray sizes = attachments.getJSONObject(j).getJSONObject("photo").getJSONArray("sizes");
							image = sizes.getJSONObject(sizes.length()-1).getString("url");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Main.data.add(new Post(image, "Пост из вк", text, (Math.random()-0.5f)*180+","+(Math.random()-0.5f)*360));
			}
			dataWriter.writeAll();
			Main.scrollFragment.recyclerAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println(result);
		}
	}
}

