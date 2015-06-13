package cc.classdiary.db;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

	private Context context;
	private SQLiteDatabase sqlLite;

	public DatabaseManager(Context context) {
		this.context = context;
		this.sqlLite = new DatabaseHelper(context).getWritableDatabase();
	}
	
	protected int selectCount(String table, String[] collumns) {
		Cursor cursor = sqlLite.query(table, collumns, null, null, null, null, null);
		int count = 0;
		if (cursor != null && cursor.moveToFirst())
			count = cursor.getCount();

		if (cursor != null)
			cursor.close();

		return count;
	}

	protected long insert(String table, ContentValues values) throws Exception {
		return sqlLite.insert(table, null, values);
	}
}
