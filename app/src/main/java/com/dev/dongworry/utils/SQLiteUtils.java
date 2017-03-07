package com.dev.dongworry.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUtils {
	private SQLiteDatabase db;
	private MyDBHelper helper;
	private static final String table_name = "Account";
	

	private SQLiteDatabase openOrCreateDB(Context context,String name) {
		helper = new MyDBHelper(context, "UCast_DB", 1);
		return helper.getReadableDatabase();
	}

//	public void insertData(String data) {
//		db = openDB();
//		db.beginTransaction();
//		try {
//			Cursor cursor = db.rawQuery("select * from "+ table_name+" where account=?", new String[]{data});
//			if(cursor.getCount()==0){
//			db.execSQL("insert into " + table_name + " values(null,?);",
//					new String[] { data });
//			}
//			db.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			db.endTransaction();
//			db.close();
//		}
//	}
//
//	public ArrayList<HashMap<String, Object>> queryData() {
//		ArrayList<HashMap<String, Object>> contents = new ArrayList<HashMap<String, Object>>();
//		db = openDB();
//		db.beginTransaction();
//		try {
//			Cursor cursor = db.query(table_name,
//					new String[] { "_id,account" }, null, null, null, null,
//					null);
//			
//			if (cursor.getCount() > 0) {
//				cursor.moveToFirst();
//				do {
//					HashMap<String, Object> map = new HashMap<String, Object>();
//					map.put("account",
//							cursor.getString(cursor.getColumnIndex("account")));
//					contents.add(map);
//				} while (cursor.moveToNext());
//			}
//			cursor.close();
//			db.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			db.endTransaction();
//			db.close();
//		}
//		return contents;
//	}

	class MyDBHelper extends SQLiteOpenHelper {

		public MyDBHelper(Context context, String name, int version) {
			super(context, name, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
//			String sql = "create table if not exists "
//					+ table_name
//					+ "(_id integer primary key autoincrement,account varchar(20));";
//			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}
}
