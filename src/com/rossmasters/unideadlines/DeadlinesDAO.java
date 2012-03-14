package com.rossmasters.unideadlines;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DeadlinesDAO {
	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] allColumns = {
		DbHelper.COLUMN_ID, DbHelper.COLUMN_LABEL, DbHelper.COLUMN_DUE, DbHelper.COLUMN_PINNED
	};
	
	public DeadlinesDAO(Context context) {
		dbHelper = new DbHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Deadline saveDeadline(Deadline deadline) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_LABEL, deadline.getLabel());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		values.put(DbHelper.COLUMN_DUE, fmt.format(deadline.getDeadline()));
		values.put(DbHelper.COLUMN_PINNED, deadline.isPinned());
		
		database.beginTransaction();
		if (deadline.getId() > 0) {
			String[] whereArgs = { String.valueOf(deadline.getId()) };
			database.update(DbHelper.TABLE_DEADLINES, values, DbHelper.COLUMN_ID + "=?", whereArgs);
		} else {
			deadline.setId(database.insert(DbHelper.TABLE_DEADLINES, null, values));
		}
		database.setTransactionSuccessful();
		database.endTransaction();
		
		return deadline;
	}
	
	public void deleteDeadline(Deadline deadline) {
		String[] whereArgs = { String.valueOf(deadline.getId()) };
		database.delete(DbHelper.TABLE_DEADLINES, DbHelper.COLUMN_ID + "=?", whereArgs);
	}
	
	public List<Deadline> fetchAll() {
		List<Deadline> deadlines = new ArrayList<Deadline>();
		
		Cursor cursor = database.query(DbHelper.TABLE_DEADLINES, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Deadline deadline = cursorToModel(cursor);
			deadlines.add(deadline);
			cursor.moveToNext();
		}
		cursor.close();
		return deadlines;
	}
	
	private Deadline cursorToModel(Cursor cursor) {
		Deadline deadline = new Deadline();
		deadline.setId(cursor.getLong(0));
		deadline.setLabel(cursor.getString(1));
		deadline.setDeadline(cursor.getString(2));
		deadline.setPinned(cursor.getInt(3) == 1);
		
		return deadline;
	}
}
