package com.labs.ex.dataHandlers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "users.db";
	private static final int SCHEMA = 1;
	public static final String DATA = "data";

	public static final String COLUMN_ID = "_id";

	public static final String COLUMN_HEADER = "header";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_TEXT = "text";
	public static final String COLUMN_COORDINATES = "coordinates";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(new StringBuilder()
				.append("CREATE TABLE IF NOT EXISTS ").append(DATA).append(" (")
				.append(COLUMN_ID)
				.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
				.append(COLUMN_IMAGE)
				.append(" TEXT, ")
				.append(COLUMN_HEADER)
				.append(" TEXT,")
				.append(COLUMN_TEXT)
//				.append(" TEXT, ")
//				.append(COLUMN_COORDINATES)
				.append(" TEXT ")
				.append(");").toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ DATA);
		onCreate(db);
	}
}


