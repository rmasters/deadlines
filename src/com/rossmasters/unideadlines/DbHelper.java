package com.rossmasters.unideadlines;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public static final String TABLE_DEADLINES = "deadlines";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_LABEL = "label";
	public static final String COLUMN_DUE = "due";
	public static final String COLUMN_PINNED = "pinned";
	
	public static final String DATABASE_NAME = "deadlines.db";
	public static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_CREATE = "create table "
		+ TABLE_DEADLINES + "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_LABEL + " text not null, "
			+ COLUMN_DUE + " datetime not null, "
			+ COLUMN_PINNED + " integer not null);";
	private static final String DATABASE_DROP = "drop table if exists " + TABLE_DEADLINES;
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DbHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to " + newVersion + " which will destroy all data.");
		db.execSQL(DATABASE_DROP);
		onCreate(db);
	}
}
