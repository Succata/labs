package com.labs.ex.dataHandlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.labs.ex.acyncTasks.LoadFromVK;
import com.labs.ex.beans.Post;
import com.labs.ex.activities.Main;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKScope;

import java.util.ArrayList;

public class BaseWriter implements DataWriter {

	DBHelper db = new DBHelper(Main.context);

	@Override
	public void read() {
		SQLiteDatabase database = db.getReadableDatabase();
		Cursor cursor = database.rawQuery("Select * from " + DBHelper.DATA, null);

		if (Main.data.size() > 0) Main.data.clear();

		cursor.moveToFirst();
		while (! cursor.isAfterLast()) {
			Main.data.add(new Post(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
			cursor.moveToNext();
		}

		if (Main.data.size() == 0) {
			ArrayList<VKScope> arrayList = new ArrayList<>();
			arrayList.add(VKScope.FRIENDS);
			arrayList.add(VKScope.WALL);
			VK.login(Main.context, arrayList);;
		};

		database.close();
		cursor.close();
	}

	@Override
	public void write() {
		SQLiteDatabase database = db.getWritableDatabase();
		Post post = Main.data.get(Main.data.size()-1);
		database.execSQL(new StringBuilder()
				.append("INSERT INTO ")
				.append(DBHelper.DATA)
				.append(" (")
				.append(DBHelper.COLUMN_IMAGE)
				.append(", ")
				.append(DBHelper.COLUMN_HEADER)
				.append(", ")
				.append(DBHelper.COLUMN_TEXT)
				.append(", ")
				.append(DBHelper.COLUMN_COORDINATES)
				.append(") VALUES (?, ?, ?, ?)")
				.toString(), new Object[] {post.imageUri, post.header, post.body, post.mapAddress});

		database.close();
	}

	@Override
	public void writeAll() {
		SQLiteDatabase database = db.getWritableDatabase();
		database.execSQL("delete from "+ DBHelper.DATA);
		for (Post post: Main.data)
			database.execSQL(new StringBuilder()
				.append("INSERT INTO ")
				.append(DBHelper.DATA)
				.append(" (")
				.append(DBHelper.COLUMN_IMAGE)
				.append(", ")
				.append(DBHelper.COLUMN_HEADER)
				.append(", ")
				.append(DBHelper.COLUMN_TEXT)
				.append(", ")
				.append(DBHelper.COLUMN_COORDINATES)
				.append(") VALUES (?, ?, ?, ?)")
				.toString(), new Object[] {post.imageUri, post.header, post.body, post.mapAddress});

		database.close();
	}

	@Override
	public void delete(int pos) {
		SQLiteDatabase database = db.getWritableDatabase();
		Post post = Main.data.get(pos);
		database.execSQL("DELETE FROM " + DBHelper.DATA + " WHERE " + DBHelper.COLUMN_IMAGE + " = ? AND " + DBHelper.COLUMN_HEADER + " = ? AND " + DBHelper.COLUMN_TEXT + " = ?", new Object[] { post.imageUri, post.header, post.body });
		Main.data.remove(pos);
		database.close();
	}

	@Override
	public void redact(int pos, String... args) {
		SQLiteDatabase database = db.getWritableDatabase();
		Post post = Main.data.get(pos);
		String s = new StringBuilder()
				.append("UPDATE ")
				.append(DBHelper.DATA)
				.append(" SET ")
				.append(DBHelper.COLUMN_IMAGE)
				.append(" = '")
				.append(args[0])
				.append("', ")
				.append(DBHelper.COLUMN_HEADER)
				.append(" = '")
				.append(args[1])
				.append("', ")
				.append(DBHelper.COLUMN_TEXT)
				.append(" = '")
				.append(args[2])
				.append("'")
				.append(" WHERE ")
				.append(DBHelper.COLUMN_IMAGE)
				.append(" = '")
				.append(post.imageUri)
				.append("' and ")
				.append(DBHelper.COLUMN_HEADER)
				.append(" = '")
				.append(post.header)
				.append("' and ")
				.append(DBHelper.COLUMN_TEXT)
				.append(" = '")
				.append(post.body)
				.append("'").toString();
		database.execSQL(s, new Object[]{});

	}

	@Override
	public void loadFromVK() {
		LoadFromVK asyncTask = new LoadFromVK(this);
		asyncTask.execute();
	}
}